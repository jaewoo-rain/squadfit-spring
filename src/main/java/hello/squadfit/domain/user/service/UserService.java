package hello.squadfit.domain.user.service;

import hello.squadfit.api.user.request.CreateUserRequest;
import hello.squadfit.api.user.request.CreateUserProfileRequest;
import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.domain.user.Role;
import hello.squadfit.domain.user.dto.CreateUserDto;
import hello.squadfit.domain.user.entity.User;
import hello.squadfit.domain.user.entity.UserProfile;
import hello.squadfit.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserJpaRepository userJpaRepository;

    @Transactional
    public Long register(CreateUserRequest createUserRequest){
        CreateUserProfileRequest profileRequest = createUserRequest.getProfile();
        UserProfile profile = UserProfile.builder()
                .role(Role.Member)
                .phone(profileRequest.getPhone())
                .name(profileRequest.getName())
                .password(profileRequest.getPassword())
                .username(profileRequest.getUsername())
                .birth(profileRequest.getBirth())
                .build();

        List<User> findUsers = userJpaRepository.findByUsername(profile.getUsername());
        if(!findUsers.isEmpty()){
            throw new IllegalStateException("이미 가입되어있는 아이디입니다.");
        }

        User user = User.createUser(new CreateUserDto(profile, createUserRequest.getNickName()));
        Long userId = userJpaRepository.save(user);
        return userId;
    }

    public User findOne(Long userId){
        User user = userJpaRepository.findOne(userId);
        return user;
    }

    public User login(LoginRequest loginRequest) {
        List<User> findUsers = userJpaRepository.findByUsername(loginRequest.getUsername());

        if (findUsers.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 아이디입니다.");
        }

        for (User user : findUsers) {
            if (user.getProfile().getPassword().equals(loginRequest.getPassword())) {
                log.info("user = {}", user);
                return user;
            }
        }

        throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
    }


}
