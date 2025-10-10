package hello.squadfit.api.Member.controller;

import hello.squadfit.api.Member.request.CreateMemberRequest;
import hello.squadfit.api.Member.request.LoginRequest;
import hello.squadfit.api.Member.response.LoginMemberResponse;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.service.MemberService;
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
@RequestMapping("api/member")
public class MemberController {

    private final MemberService memberService;

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request, BindingResult bindingResult){
//
//        if(bindingResult.hasErrors()){
//
//            log.info("로그인 오류 = {}", bindingResult);
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
//
//        }
//
//        Member loginMember = memberService.login(request);
//        LoginMemberResponse result = LoginMemberResponse.builder()
//                .level(loginMember.getLevel())
//                .point(loginMember.getPoint())
//                .nickName(loginMember.getNickName())
//                .requiredExperience(loginMember.getRequiredExperience())
//                .availableReportCount(loginMember.getAvailableReportCount())
//                .build();
//        return ResponseEntity.status(200).body(result);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateMemberRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("회원가입 오류 = {}", bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
        }
        Long memberId = memberService.join(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);

    }


}
