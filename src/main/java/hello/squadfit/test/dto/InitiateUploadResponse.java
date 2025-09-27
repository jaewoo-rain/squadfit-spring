package hello.squadfit.test.dto;

public record InitiateUploadResponse(
        String uploadId,
        int chunkSize
) {}
