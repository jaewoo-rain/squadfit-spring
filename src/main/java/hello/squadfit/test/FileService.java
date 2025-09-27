//package hello.squadfit.test;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.*;
//
//@Transactional(readOnly = true)
//@Service
//public class FileService {
//
//    // application.yml 또는 properties에서 설정한 "파일 저장 위치"
//    @Value("${file.upload-dir}")
//    private String storageAddress;
//
//    /** 저장할 기본 폴더 위치를 Path 객체로 바꿔주는 메서드 */
//    private Path baseDir() {
//        // 입력받은 문자열 경로를 절대 경로로 바꾸고(normalize: 정리)
//        return Paths.get(storageAddress).toAbsolutePath().normalize();
//    }
//
//    /** 파일 업로드: 사용자가 올린 파일을 서버에 저장한다 */
//    @Transactional
//    public String fileUploadOnServer(MultipartFile uploadFile) {
//        try {
//            Path base = baseDir();
//            // 폴더가 없다면 자동으로 만들어준다
//            Files.createDirectories(base);
//
//            // getFileName()을 써서 안전하게 파일명만 추출한다
//            String safeFileName = Paths.get("jaewoo" + uploadFile.getOriginalFilename())
//                    .getFileName().toString();
//
//            // 최종 저장 위치 (기본폴더 + 파일명)
//            Path target = base.resolve(safeFileName).normalize();
//
//            // 안전 검사: 혹시라도 기본 폴더 밖으로 나가려는 경우 막는다
//            if (!target.startsWith(base)) {
//                throw new SecurityException("폴더 밖으로 나갈 수 없음!");
//            }
//
//            // 파일을 실제로 복사해서 저장한다
//            uploadFile.transferTo(target.toFile());
//
//            // 나중에 다시 찾을 수 있도록 파일 이름을 반환한다
//            return safeFileName;
//        } catch (IOException e) {
//            throw new IllegalStateException("파일 업로드 실패", e);
//        }
//    }
//
//    /** 파일 꺼내오기: 서버에 저장된 파일을 찾아서 돌려준다 */
//    public UrlResource getFileFromServer(String fileName) {
//        try {
//            // 파일명만 안전하게 추출
//            String safe = Paths.get(fileName).getFileName().toString();
//            Path target = baseDir().resolve(safe).normalize();
//
//            // 안전 검사
//            if (!target.startsWith(baseDir())) {
//                throw new SecurityException("폴더 밖으로 나갈 수 없음!");
//            }
//            // 파일이 없다면 에러
//            if (!Files.exists(target)) {
//                throw new NoSuchFileException(target.toString());
//            }
//            // 파일 경로를 URI(주소)로 바꿔서 UrlResource로 반환
//            return new UrlResource(target.toUri());
//        } catch (MalformedURLException e) {
//            throw new IllegalStateException("잘못된 파일 주소", e);
//        } catch (IOException e) {
//            throw new IllegalStateException("파일 불러오기 실패", e);
//        }
//    }
//
//    /** 파일 종류 알아내기: 이미지인지 영상인지 확인한다 */
//    public MediaType getMediaType(String fileName) {
//        try {
//            String safe = Paths.get(fileName).getFileName().toString();
//            Path target = baseDir().resolve(safe).normalize();
//
//            // 안전 검사
//            if (!target.startsWith(baseDir())) {
//                throw new SecurityException("폴더 밖으로 나갈 수 없음!");
//            }
//
//            // OS가 파일 확장자를 보고 MIME 타입 추측
//            String contentType = Files.probeContentType(target);
//
//            // 만약 못 알아내면 확장자를 직접 체크
//            if (contentType == null) {
//                String fn = safe.toLowerCase();
//                if (fn.endsWith(".png"))        contentType = "image/png";
//                else if (fn.endsWith(".jpg") || fn.endsWith(".jpeg")) contentType = "image/jpeg";
//                else if (fn.endsWith(".gif"))   contentType = "image/gif";
//                else if (fn.endsWith(".mp4"))   contentType = "video/mp4";
//                else                            contentType = "application/octet-stream";
//            }
//            return MediaType.parseMediaType(contentType);
//        } catch (IOException e) {
//            throw new IllegalStateException("MIME 타입 판별 실패", e);
//        }
//    }
//
//    /** 파일 삭제: 서버에 있는 파일을 지운다 */
//    @Transactional
//    public void deleteFile(String fileName) {
//        try {
//            String safe = Paths.get(fileName).getFileName().toString();
//            Path target = baseDir().resolve(safe).normalize();
//
//            // 안전 검사
//            if (!target.startsWith(baseDir())) {
//                throw new SecurityException("폴더 밖으로 나갈 수 없음!");
//            }
//            // 파일이 있으면 삭제
//            Files.deleteIfExists(target);
//        } catch (IOException e) {
//            throw new IllegalStateException("파일 삭제 실패", e);
//        }
//    }
//}
//
