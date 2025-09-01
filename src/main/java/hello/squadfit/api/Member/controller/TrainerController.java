package hello.squadfit.api.Member.controller;

import hello.squadfit.api.Member.request.CreateTrainerRequest;
import hello.squadfit.api.Member.request.LoginRequest;
import hello.squadfit.api.Member.response.LoginTrainerResponse;
import hello.squadfit.api.Member.response.TrainerInfoResponse;
import hello.squadfit.domain.member.entity.Trainer;
import hello.squadfit.domain.member.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/trainer")
public class TrainerController {

    private final TrainerService trainerService;

    // 트레이너 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("로그인 오류 = {}", bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
        }

        Trainer loginTrainer = trainerService.login(request);
        LoginTrainerResponse result = LoginTrainerResponse.builder()
                .name(loginTrainer.getProfile().getName())
                .place(loginTrainer.getPlace())
                .build();

        return ResponseEntity.status(200).body(result);
    }

    // 트레이너 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateTrainerRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("회원가입 오류 = {}", bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
        }
        Long memberId = trainerService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);

    }

    /**
     * 트레이너가 작성한 코멘트 리스트 받기
     * todo: 코멘트 작성시 해야할듯?
     */
//    @GetMapping("/comments")
//    public ResponseEntity<?> getAllComments(){
//
//    }

    /**
     * 트레이너 관련 정보 받기 todo: 트레이너 정보 추가적으로 어떤거 넣을지 의논하기
     */
    @GetMapping("/info/{trainerId}")
    public ResponseEntity<TrainerInfoResponse> getInfo(@PathVariable("trainerId") Long trainerId){

        Trainer trainer = trainerService.findOne(trainerId);
        TrainerInfoResponse result = TrainerInfoResponse.builder()
                .name(trainer.getProfile().getName())
                .phone(trainer.getProfile().getPhone())
                .place(trainer.getPlace())
                .build();

        return ResponseEntity.ok(result);

    }
    /**
     * 이름으로 트레이너 찾기 todo: 동적쿼리 이용해서 장소나 다른 정보로도 찾을수 있도록 하기
     */
    @GetMapping("/search")
    public ResponseEntity<?> findTrainerByName(@RequestParam("name") String name){

        List<Trainer> trainers =  trainerService.findAllByName(name);

        List<TrainerInfoResponse> result = trainers.stream()
                .map(trainer -> TrainerInfoResponse.builder()
                    .name(trainer.getProfile().getName())
                    .phone(trainer.getProfile().getPhone())
                    .place(trainer.getPlace())
                    .build()
                ).toList();

        return ResponseEntity.ok(result);
    }


}
