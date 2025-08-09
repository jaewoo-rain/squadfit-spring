package hello.squadfit.api.Member.controller;

import hello.squadfit.api.Member.request.CreateMemberProfileRequest;
import hello.squadfit.api.Member.request.CreateTrainerRequest;
import hello.squadfit.api.Member.request.LoginRequest;
import hello.squadfit.api.Member.response.LoginMemberResponse;
import hello.squadfit.domain.member.entity.Trainer;
import hello.squadfit.domain.member.service.TrainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/trainer")
public class TrainerController {
    // todo: postman 실험해봐야함

    private final TrainerService trainerService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("로그인 오류 = {}", bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
        }

        Trainer loginTrainer = trainerService.login(request);

        return ResponseEntity.status(200).body(loginTrainer.getPlace());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateTrainerRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("회원가입 오류 = {}", bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
        }
        Long memberId = trainerService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);

    }


}
