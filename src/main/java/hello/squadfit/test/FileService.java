package hello.squadfit.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Transactional(readOnly = true)
public class FileService {

    private final String STORAGE_ADDRESS = "/";

    public void fileUploadOnServer(MultipartFile uploadFile) {
        String fullPath = STORAGE_ADDRESS + uploadFile.getOriginalFilename();
        try{
            uploadFile.transferTo(new File(fullPath));
        }catch (IOException e){
            throw new IllegalStateException();
        }
    }
}
