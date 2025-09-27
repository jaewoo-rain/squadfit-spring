package hello.squadfit.test.service;


import hello.squadfit.test.dto.CompleteUploadRequest;
import hello.squadfit.test.dto.InitiateUploadRequest;
import hello.squadfit.test.dto.InitiateUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ChunkedFileService {

    @Value("${file.upload-dir}")
    private String uploadDirStr;

    @Value("${file.temp-dir:}")
    private String tempDirStr; // 없으면 uploadDir 아래에 tmp 생성

    @Value("${file.default-chunk-size:5242880}")
    private int defaultChunkSize;

    /** 업로드 루트 */
    private Path uploadDir() {
        return Paths.get(uploadDirStr).toAbsolutePath().normalize();
    }

    /** 임시 청크 저장 루트 */
    private Path tempDir() throws IOException {
        Path base = (StringUtils.hasText(tempDirStr)
                ? Paths.get(tempDirStr)
                : uploadDir().resolve("_tmp"))
                .toAbsolutePath().normalize();
        Files.createDirectories(base);
        return base;
    }

    /** 업로드 초기화: uploadId 발급 & 청크 폴더 준비 */
    @Transactional
    public InitiateUploadResponse initiateUpload(InitiateUploadRequest req, Long memberId) {
        try {
            String uploadId = UUID.randomUUID().toString();
            // 예: {temp}/memberId/{uploadId}/
            Path base = tempDir();
            Path parent = (memberId == null)
                    ? base.resolve(uploadId)
                    : base.resolve(String.valueOf(memberId)).resolve(uploadId);
            Files.createDirectories(parent);

            // 메타파일 저장: 총 크기, 원본 파일명 등
            Path meta = parent.resolve(".meta");
            Files.writeString(meta,
                    "filename=" + req.filename() + System.lineSeparator() +
                            "totalSize=" + req.totalSize() + System.lineSeparator(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            return new InitiateUploadResponse(uploadId, defaultChunkSize);
        } catch (IOException e) {
            throw new IllegalStateException("initiateUpload failed", e);
        }
    }

    /** 청크 업로드: index 기반 .part 파일로 적재 */
    @Transactional
    public void uploadChunk(String uploadId, Long memberId, int index, MultipartFile chunk) {
        try {
            if (index < 0) throw new IllegalArgumentException("index must be >= 0");
            if (chunk == null || chunk.isEmpty()) throw new IllegalArgumentException("empty chunk");

            Path parent = chunkParentDir(uploadId, memberId);

            // 청크 파일명: 00000001.part 같은 zero-pad로 정렬 안전
            String partName = String.format("%08d.part", index);
            Path part = parent.resolve(partName).normalize();

            // 보안: 상위 경로 탈출 방지
            if (!part.startsWith(parent)) throw new SecurityException("Invalid path");

            try (InputStream in = chunk.getInputStream()) {
                Files.copy(in, part, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new IllegalStateException("uploadChunk failed", e);
        }
    }

    /** 업로드 완료: 청크들을 병합 → 최종 파일 생성 */
    @Transactional
    public String completeUpload(CompleteUploadRequest req, Long memberId) {
        try {
            // 최종 파일명 정제(파일명만)
            String safeName = Paths.get(req.filename()).getFileName().toString();

            // 최종 저장 경로(선택) 회원별 폴더 분리
            Path userDir = (memberId == null)
                    ? uploadDir()
                    : uploadDir().resolve(String.valueOf(memberId));
            Files.createDirectories(userDir);

            Path finalPath = userDir.resolve(safeName).normalize();
            if (!finalPath.startsWith(uploadDir())) throw new SecurityException("Invalid path");

            // 청크 폴더
            Path parent = chunkParentDir(req.uploadId(), memberId);
            if (!Files.isDirectory(parent)) throw new IllegalStateException("No such uploadId");

            // 청크 파일들 정렬
            List<Path> parts = Files.list(parent)
                    .filter(p -> p.getFileName().toString().endsWith(".part"))
                    .sorted(Comparator.comparing(Path::getFileName))
                    .toList();

            if (parts.isEmpty()) throw new IllegalStateException("No chunks found");

            // 병합(덮어쓰기 허용: 필요시 존재 검사 후 unique 이름 생성)
            Files.deleteIfExists(finalPath);
            Files.createFile(finalPath);

            for (Path part : parts) {
                Files.write(finalPath, Files.readAllBytes(part), StandardOpenOption.APPEND);
            }

            // 임시 폴더 정리
            deleteDirectoryRecursively(parent);

            return safeName; // 최종 파일명 반환
        } catch (IOException e) {
            throw new IllegalStateException("completeUpload failed", e);
        }
    }

    /** 파일을 UrlResource로 로드 (다운로드/스트리밍 용) */
    @Transactional(readOnly = true)
    public UrlResource loadAsResource(Long memberId, String filename) {
        try {
            String safe = Paths.get(filename).getFileName().toString();
            Path userDir = (memberId == null)
                    ? uploadDir()
                    : uploadDir().resolve(String.valueOf(memberId));
            Path target = userDir.resolve(safe).toAbsolutePath().normalize();

            if (!target.startsWith(uploadDir())) throw new SecurityException("Invalid path");
            if (!Files.exists(target)) throw new java.nio.file.NoSuchFileException(target.toString());

            return new UrlResource(target.toUri());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("Malformed file URI", e);
        } catch (IOException e) {
            throw new IllegalStateException("File load failed", e);
        }
    }

    /** MIME 타입 추론 */
    @Transactional(readOnly = true)
    public MediaType getMediaType(Long memberId, String filename) {
        try {
            String safe = Paths.get(filename).getFileName().toString();
            Path userDir = (memberId == null)
                    ? uploadDir()
                    : uploadDir().resolve(String.valueOf(memberId));
            Path target = userDir.resolve(safe).toAbsolutePath().normalize();

            if (!target.startsWith(uploadDir())) throw new SecurityException("Invalid path");

            String contentType = Files.probeContentType(target);
            if (contentType == null) contentType = guessFallbackByExt(safe);
            return MediaType.parseMediaType(contentType);
        } catch (IOException e) {
            throw new IllegalStateException("Detect media type failed", e);
        }
    }

    // ---------- 내부 유틸 ----------

    private Path chunkParentDir(String uploadId, Long memberId) throws IOException {
        Path base = tempDir();
        Path parent = (memberId == null)
                ? base.resolve(uploadId)
                : base.resolve(String.valueOf(memberId)).resolve(uploadId);
        Files.createDirectories(parent);
        return parent.toAbsolutePath().normalize();
    }

    private static void deleteDirectoryRecursively(Path dir) throws IOException {
        if (!Files.exists(dir)) return;
        try (var s = Files.walk(dir)) {
            s.sorted(Comparator.reverseOrder()).forEach(p -> {
                try { Files.deleteIfExists(p); } catch (IOException ignored) {}
            });
        }
    }

    private static String guessFallbackByExt(String name) {
        String n = name.toLowerCase();
        if (n.endsWith(".png"))  return "image/png";
        if (n.endsWith(".jpg") || n.endsWith(".jpeg")) return "image/jpeg";
        if (n.endsWith(".gif"))  return "image/gif";
        if (n.endsWith(".mp4"))  return "video/mp4";
        if (n.endsWith(".webm")) return "video/webm";
        if (n.endsWith(".mp3"))  return "audio/mpeg";
        return "application/octet-stream";
    }
}
