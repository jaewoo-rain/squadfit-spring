package hello.squadfit.domain.member.controller;

import hello.squadfit.domain.member.request.CreateMemberRequest;
import hello.squadfit.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateMemberRequest request, BindingResult bindingResult, HttpServletResponse response){

        if(bindingResult.hasErrors()){
            log.info("회원가입 오류 = {}", bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(bindingResult.toString());
        }
        Long memberId = memberService.join(request, response);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberId);

    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsMember(@RequestParam(name = "username") String username){
        boolean existed = memberService.existsMemberByUsername(username);
        return ResponseEntity.ok(existed);
    }


}
