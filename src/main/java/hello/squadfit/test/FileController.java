package hello.squadfit.test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @PostMapping
    public void fileUpload(@RequestPart MultipartFile uploadFile){
        fileService.fileUploadOnServer(uploadFile);
    }

}
