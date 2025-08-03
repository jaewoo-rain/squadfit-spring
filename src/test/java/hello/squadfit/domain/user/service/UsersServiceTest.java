package hello.squadfit.domain.user.service;

import hello.squadfit.api.user.request.CreateUserProfileRequest;
import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.domain.user.entity.Users;
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
class UsersServiceTest {

    @Autowired UserService userService;

    @Test
    public void register(){
        CreateUserProfileRequest createUserProfileRequest =
                new CreateUserProfileRequest(
                        "test","1234","991111"
                        ,"010-1234-5678","jaewoo","yang");

        Long userId = userService.register(createUserProfileRequest);
        Optional<Users> user = userService.findOne(userId);
        Users findUsers = user.orElse(null);

        assertThat(findUsers.getNickName()).isEqualTo(createUserProfileRequest.getNickName());

    }

    @Test
    public void login(){

        LoginRequest loginRequest = new LoginRequest("test", "1234");
        Users user = userService.login(loginRequest);

        assertThat(user.getProfile().getUsername()).isEqualTo(loginRequest.getUsername());
        assertThat(user.getProfile().getPassword()).isEqualTo(loginRequest.getPassword());

    }
  
}