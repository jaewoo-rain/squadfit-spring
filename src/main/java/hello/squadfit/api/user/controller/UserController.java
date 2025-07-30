package hello.squadfit.api.user.controller;

import hello.squadfit.api.user.request.CreateUserRequest;
import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.api.user.request.RegisterRequest;
import hello.squadfit.domain.user.Role;
import hello.squadfit.domain.user.entity.User;
import hello.squadfit.domain.user.entity.UserProfile;
import hello.squadfit.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute RegisterRequest request, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.info("회원가입 오류 = {}", bindingResult);
//            return "오류나옴" + bindingResult;
        }
//        UserProfile userProfile = new UserProfile(request.getUsername(), request.getPassword(), request.getBirth(), request.getPhone(), request.getName(), Role.Member);
        userService.register()


    }


}
