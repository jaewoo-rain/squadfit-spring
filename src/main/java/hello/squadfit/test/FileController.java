//package hello.squadfit.test;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/file")
//public class FileController {
//
//    private final FileService fileService;
//
//    @PostMapping
//    public ResponseEntity<String> fileUpload(@RequestPart(name = "uploadFile") MultipartFile uploadFile){
//        String fileName = fileService.fileUploadOnServer(uploadFile);
//        return ResponseEntity.ok(fileName);
//    }
//
//    @GetMapping
//    public ResponseEntity<UrlResource> getFile(@RequestParam(name = "fileName") String fileName){
//
//        UrlResource fileFromServer = fileService.getFileFromServer(fileName);
//        MediaType mediaType = fileService.getMediaType(fileName);
//
//        return ResponseEntity.ok()
//                .contentType(mediaType)
//                .body(fileFromServer);
//    }
//
//}
