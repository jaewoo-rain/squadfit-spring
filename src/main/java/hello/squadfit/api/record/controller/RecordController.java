package hello.squadfit.api.record.controller;

import hello.squadfit.api.record.request.SaveRecordRequest;
import hello.squadfit.api.user.response.AllRecordResponse;
import hello.squadfit.api.user.response.SingleRecordResponse;
import hello.squadfit.domain.record.service.RecordService;
import hello.squadfit.domain.member.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService recordService;
    private final UserService userService;

    @Operation(summary = "운동 기록 저장")
    @PostMapping
    public ResponseEntity<Long> saveRecord(@Valid @RequestBody SaveRecordRequest request, BindingResult bindingResult){

        if(!userService.existsMember(request.getUserId())){
            throw new IllegalStateException("유저가 존재하지 않습니다.");
        }

        if (bindingResult.hasErrors()) {
            log.info("비어있음 = {}", bindingResult);

            // 에러 메시지 수집
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .toList();

            throw new IllegalStateException("필요창이 비어있음");
            // todo: 에러 처리
//            return ResponseEntity.badRequest();
        }

        Long saveId = recordService.save(request);

        return ResponseEntity.ok(saveId);
    }
    
    @Operation(summary = "유저 운동 기록 전체 조회")
    @GetMapping("/all/{memberId}")
    public ResponseEntity<AllRecordResponse> findAllRecord(@PathVariable Long memberId){
        if(!userService.existsMember(memberId)){
            throw new IllegalStateException("유저가 존재하지 않습니다.");
        }

        AllRecordResponse result = recordService.findAll(memberId);

        return ResponseEntity.ok(result); // 없으면 null

    }

    @Operation(summary = "유저 운동 기록 단일 조회")
    @GetMapping("/single/{memberId}/{exerciseId}")
    public ResponseEntity<Optional<SingleRecordResponse>> findOneRecord(@PathVariable Long memberId, @PathVariable Long exerciseId){

        if(!userService.existsMember(memberId)){
            throw new IllegalStateException("유저가 존재하지 않습니다.");
        }

        Optional<SingleRecordResponse> result = recordService.findOne(memberId, exerciseId);

        return ResponseEntity.ok(result); // 없으면 nul 처리
    }

    @DeleteMapping("/{memberId}/{exerciseId}")
    public ResponseEntity<Long> deleteRecord(@PathVariable Long memberId, @PathVariable Long exerciseId){

        if(!userService.existsMember(memberId)){
            throw new IllegalStateException("유저가 존재하지 않습니다.");
        }

        Long removeRecordId = recordService.remove(exerciseId);
        return ResponseEntity.ok(removeRecordId);

    }

    // TODO: 유형별 조회
    // TODO: 랭크 조회


}
