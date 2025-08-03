package hello.squadfit.api.record.controller;

import hello.squadfit.api.record.request.SaveRecordRequest;
import hello.squadfit.domain.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<?> saveRecord(@Valid @RequestBody SaveRecordRequest request){

        Long save = recordService.save(request);

        return ResponseEntity.status(HttpStatus.OK).body(save);
    }

}
