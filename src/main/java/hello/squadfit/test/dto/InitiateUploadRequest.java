package hello.squadfit.test.dto;

public record InitiateUploadRequest(
        String filename,
        long totalSize
) {}
