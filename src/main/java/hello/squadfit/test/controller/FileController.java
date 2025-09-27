package hello.squadfit.test.controller;

import hello.squadfit.test.dto.CompleteUploadRequest;
import hello.squadfit.test.dto.InitiateUploadRequest;
import hello.squadfit.test.dto.InitiateUploadResponse;
import hello.squadfit.test.service.ChunkedFileService;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final ChunkedFileService fileService;

    public FileController(ChunkedFileService fileService) {
        this.fileService = fileService;
    }

    /** 업로드 시작: uploadId 발급 */
    @PostMapping("/initiate")
    public InitiateUploadResponse initiate(@RequestBody InitiateUploadRequest req,
                                           @RequestParam(required = false) Long memberId
    ) {
        return fileService.initiateUpload(req, memberId);
    }

    /** 청크 업로드: multipart/form-data (field: chunk) */
    @PostMapping(value = "/chunk", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadChunk(@RequestParam String uploadId,
                                            @RequestParam int index,
                                            @RequestPart("chunk") MultipartFile chunk,
                                            @RequestParam(required = false) Long memberId
    ){
        fileService.uploadChunk(uploadId, memberId, index, chunk);
        return ResponseEntity.accepted().build();
    }

    /** 업로드 완료: 병합 실행 */
    @PostMapping("/complete")
    public ResponseEntity<String> complete(@RequestBody CompleteUploadRequest req,
                                           @RequestParam(required = false) Long memberId) {
        String finalName = fileService.completeUpload(req, memberId);
        return ResponseEntity.ok(finalName);
    }

    /** 다운로드(스트리밍): Range 헤더 지원 */
    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<StreamingResponseBody> download(@RequestParam String filename,
                                                          @RequestParam(required = false) Long memberId,
                                                          @RequestHeader(value = "Range", required = false) String rangeHeader)
            throws IOException {

        UrlResource resource = fileService.loadAsResource(memberId, filename);
        MediaType mediaType = fileService.getMediaType(memberId, filename);

        long fileLength = resource.contentLength();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        // 파일명 헤더 (한글 대응)
        String encoded = java.net.URLEncoder.encode(resource.getFilename(), java.nio.charset.StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + resource.getFilename() + "\"; filename*=UTF-8''" + encoded);

        // Range 요청 처리
        if (rangeHeader != null && rangeHeader.startsWith("bytes=")) {
            List<HttpRange> ranges = HttpRange.parseRanges(rangeHeader);
            HttpRange range = ranges.get(0); // 단일 범위만 처리
            long start = range.getRangeStart(fileLength);
            long end = range.getRangeEnd(fileLength);
            long contentLength = end - start + 1;

            headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
            headers.add(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileLength);

            StreamingResponseBody body = outputStream -> {
                try (InputStream in = resource.getInputStream()) {
                    in.skip(start);
                    long remaining = contentLength;
                    byte[] buffer = new byte[8192];
                    int read;
                    while (remaining > 0 && (read = in.read(buffer, 0, (int)Math.min(buffer.length, remaining))) != -1) {
                        outputStream.write(buffer, 0, read);
                        remaining -= read;
                    }
                }
            };

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .headers(headers)
                    .contentLength(contentLength)
                    .body(body);
        }

        // Range 없으면 전체 전송
        StreamingResponseBody body = outputStream -> {
            try (InputStream in = resource.getInputStream()) {
                in.transferTo(outputStream);
            }
        };

        headers.add(HttpHeaders.ACCEPT_RANGES, "bytes");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileLength)
                .body(body);
    }
}
