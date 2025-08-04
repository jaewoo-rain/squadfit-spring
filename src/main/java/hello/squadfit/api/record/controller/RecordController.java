package hello.squadfit.api.record.controller;

import hello.squadfit.api.ResponseDto;
import hello.squadfit.api.record.request.SaveRecordRequest;
import hello.squadfit.api.user.response.SingleRecordResponse;
import hello.squadfit.domain.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<?> saveRecord(@Valid @RequestBody SaveRecordRequest request, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            log.info("비어있음 = {}", bindingResult);

            // 에러 메시지 수집
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();

            ResponseDto<List<String>> errorResponse = new ResponseDto<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "요청 값이 유효하지 않습니다.",
                    errors
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

        Long saveId = recordService.save(request);
        ResponseDto<Long> response = new ResponseDto<>(HttpStatus.OK.value(), "저장 성공", saveId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> findAllRecord(@PathVariable Long userId){

        List<SingleRecordResponse> result = recordService.findAll(userId);

        ResponseDto<List<SingleRecordResponse>> response = new ResponseDto<>(HttpStatus.OK.value(), "전체 운동조회 성공", result);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
