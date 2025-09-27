package hello.squadfit.test.dto;

public record CompleteUploadRequest(
        String uploadId,
        String filename,    // 최종 파일명 (확장자 포함)
        Long memberId       // 선택: 회원별 폴더 분리 원하면 사용
) {}
