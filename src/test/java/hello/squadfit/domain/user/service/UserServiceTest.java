package hello.squadfit.domain.user.service;

import hello.squadfit.api.user.request.CreateUserProfileRequest;
import hello.squadfit.api.user.request.CreateUserRequest;
import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired private UserService userService;

    @BeforeEach
    public void init(){
        CreateUserProfileRequest profileRequest = new CreateUserProfileRequest();
        profileRequest.setBirth("991111");
        profileRequest.setPassword("1234");
        profileRequest.setName("park");
        profileRequest.setPhone("01000000000");
        profileRequest.setUsername("testId");

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setNickName("testNickname");
        userRequest.setProfile(profileRequest);

        Long saveUserId = userService.register(userRequest);
        User findUser = userService.findOne(saveUserId);
    }

    @Test
    @Transactional
    public void 회원가입_중복() throws Exception{
        // given
        CreateUserProfileRequest profileRequest = new CreateUserProfileRequest();
        profileRequest.setBirth("991111");
        profileRequest.setPassword("1234");
        profileRequest.setName("park");
        profileRequest.setPhone("01000000000");
        profileRequest.setUsername("testId");

        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setNickName("testNickname");
        userRequest.setProfile(profileRequest);

        // when
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> userService.register(userRequest));
        assertThat(exception.getMessage()).isEqualTo("이미 가입되어있는 아이디입니다.");


        // then
    }

    @Test
    public void 로그인() throws Exception{
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("1234");
        loginRequest.setUsername("testId");
        // when
        User loginUser = userService.login(loginRequest);

        // then
        assertThat(loginUser.getNickName()).isEqualTo("testNickname");
    }

    @Test
    public void 로그인_아이디틀림() throws Exception{
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("1234");
        loginRequest.setUsername("testId123");
        // when

        // then
        assertThrows(IllegalStateException.class, () -> userService.login(loginRequest));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.login(loginRequest);
        });
        assertEquals("존재하지 않는 아이디입니다.", exception.getMessage());
    }

    @Test
    public void 로그인_비밀번호틀림() throws Exception{
        // given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("1234123");
        loginRequest.setUsername("testId");
        // when

        // then
        assertThrows(IllegalStateException.class, () -> userService.login(loginRequest));
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            userService.login(loginRequest);
        });
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
    }

}