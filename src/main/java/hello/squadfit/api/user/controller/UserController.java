package hello.squadfit.api.user.controller;

import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.domain.user.entity.User;
import hello.squadfit.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(@Validated @ModelAttribute LoginRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("로그인 오류 = {}", bindingResult);
            return "오류나옴" + bindingResult;
        }

        User loginUser = userService.login(request);
        return loginUser.getNickName();


    }
}
