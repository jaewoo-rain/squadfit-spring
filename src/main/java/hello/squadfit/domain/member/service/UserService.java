package hello.squadfit.domain.member.service;

import hello.squadfit.api.Member.request.CreateMemberProfileRequest;
import hello.squadfit.api.Member.request.LoginRequest;
import hello.squadfit.domain.member.Role;
import hello.squadfit.domain.member.dto.CreateUserDto;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.entity.MemberProfile;
import hello.squadfit.domain.member.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    @Transactional
    public Long register(CreateMemberProfileRequest request){

        MemberProfile profile = MemberProfile.builder()
                .role(Role.Member)
                .phone(request.getPhone())
                .name(request.getName())
                .password(request.getPassword())
                .username(request.getUsername())
                .birth(request.getBirth())
                .build();

        Optional<Member> findUser = memberRepository.findByProfileUsername(profile.getUsername());
        if(!findUser.isEmpty()){
            throw new IllegalStateException("이미 가입되어있는 아이디입니다.");
        }

        Member member = Member.createUser(new CreateUserDto(profile, request.getNickName()));
        Member save = memberRepository.save(member);
        return save.getId();
    }

    public Optional<Member> findOne(Long userId){
        return memberRepository.findById(userId);
    }

    public Member login(LoginRequest loginRequest) {
        Member findMember = memberRepository.findByProfileUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));

        if(!findMember.getProfile().getPassword().equals(loginRequest.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        log.info("getPoint = {}", findMember.getPoint());

        return findMember;
    }

    // 유저 존재하는지 확인
    public boolean existsMember(Long userId){
        return memberRepository.existsMemberById(userId);
    }


}
