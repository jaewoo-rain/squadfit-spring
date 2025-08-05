package hello.squadfit.api.user.controller;

import hello.squadfit.api.user.request.CreateUserProfileRequest;
import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.api.user.response.LoginResponse;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("로그인 오류 = {}", bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
        }

        Member loginMember = userService.login(request);
        LoginResponse result = LoginResponse.builder()
                .level(loginMember.getLevel())
                .point(loginMember.getPoint())
                .nickName(loginMember.getNickName())
                .requiredExperience(loginMember.getRequiredExperience())
                .availableReportCount(loginMember.getAvailableReportCount())
                .build();
        return ResponseEntity.status(200).body(result);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserProfileRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("회원가입 오류 = {}", bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
        }
        Long userId = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);

    }


}
