package hello.squadfit.domain.member.service;

import hello.squadfit.api.user.request.CreateUserProfileRequest;
import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

    @Autowired UserService userService;

    @Test
    public void register(){
        CreateUserProfileRequest createUserProfileRequest =
                new CreateUserProfileRequest(
                        "test","1234","991111"
                        ,"010-1234-5678","jaewoo","yang");

        Long userId = userService.register(createUserProfileRequest);
        Optional<Member> user = userService.findOne(userId);
        Member findMember = user.orElse(null);

        assertThat(findMember.getNickName()).isEqualTo(createUserProfileRequest.getNickName());

    }

    @Test
    public void login(){

        LoginRequest loginRequest = new LoginRequest("test", "1234");
        Member user = userService.login(loginRequest);

        assertThat(user.getProfile().getUsername()).isEqualTo(loginRequest.getUsername());
        assertThat(user.getProfile().getPassword()).isEqualTo(loginRequest.getPassword());

    }
  
}