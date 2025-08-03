package hello.squadfit.domain.user.service;

import hello.squadfit.api.user.request.CreateUserProfileRequest;
import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.domain.user.Role;
import hello.squadfit.domain.user.dto.CreateUserDto;
import hello.squadfit.domain.user.entity.Users;
import hello.squadfit.domain.user.entity.UserProfile;
import hello.squadfit.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long register(CreateUserProfileRequest request){

        UserProfile profile = UserProfile.builder()
                .role(Role.Member)
                .phone(request.getPhone())
                .name(request.getName())
                .password(request.getPassword())
                .username(request.getUsername())
                .birth(request.getBirth())
                .build();

        Optional<Users> findUser = userRepository.findByProfileUsername(profile.getUsername());
        if(!findUser.isEmpty()){
            throw new IllegalStateException("이미 가입되어있는 아이디입니다.");
        }

        Users users = Users.createUser(new CreateUserDto(profile, request.getNickName()));
        Users save = userRepository.save(users);
        return save.getId();
    }

    public Optional<Users> findOne(Long userId){
        return userRepository.findById(userId);
    }

    public Users login(LoginRequest loginRequest) {
        Users findUsers = userRepository.findByProfileUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));

        if(!findUsers.getProfile().getPassword().equals(loginRequest.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        log.info("getPoint = {}", findUsers.getPoint());

        return findUsers;
    }


}
