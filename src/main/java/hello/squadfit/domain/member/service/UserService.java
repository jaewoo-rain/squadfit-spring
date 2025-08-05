package hello.squadfit.domain.member.service;

import hello.squadfit.api.user.request.CreateUserProfileRequest;
import hello.squadfit.api.user.request.LoginRequest;
import hello.squadfit.domain.member.Role;
import hello.squadfit.domain.member.dto.CreateUserDto;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.entity.UserProfile;
import hello.squadfit.domain.member.repository.UserRepository;
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

        Optional<Member> findUser = userRepository.findByProfileUsername(profile.getUsername());
        if(!findUser.isEmpty()){
            throw new IllegalStateException("이미 가입되어있는 아이디입니다.");
        }

        Member member = Member.createUser(new CreateUserDto(profile, request.getNickName()));
        Member save = userRepository.save(member);
        return save.getId();
    }

    public Optional<Member> findOne(Long userId){
        return userRepository.findById(userId);
    }

    public Member login(LoginRequest loginRequest) {
        Member findMember = userRepository.findByProfileUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));

        if(!findMember.getProfile().getPassword().equals(loginRequest.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        log.info("getPoint = {}", findMember.getPoint());

        return findMember;
    }

    // 유저 존재하는지 확인
    public boolean existsMember(Long userId){
        return userRepository.existsMemberById(userId);
    }


}
