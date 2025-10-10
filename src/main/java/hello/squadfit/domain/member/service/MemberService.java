package hello.squadfit.domain.member.service;

import hello.squadfit.api.Member.request.CreateMemberRequest;
import hello.squadfit.api.Member.request.LoginRequest;
import hello.squadfit.domain.member.Role;
import hello.squadfit.domain.member.dto.CreateMemberDto;
import hello.squadfit.domain.member.dto.CreateUserDto;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.entity.UserEntity;
import hello.squadfit.domain.member.repository.MemberRepository;
import hello.squadfit.domain.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Transactional
    public Long join(CreateMemberRequest request){

        if(existsMemberByUsername(request.getUsername())){
            throw new IllegalStateException("이미 가입되어있는 아이디입니다.");
        }

        // user 만들기
        UserEntity userEntity = userService.join(CreateUserDto.from(request));
        
        // member 만들기
        Member member = Member.create(CreateMemberDto.from(request), userEntity);
        Member save = memberRepository.save(member);

        return save.getId();
    }

    // userId 존재하는지 확인
    public boolean existsMemberByMemberId(Long memberId){
        return memberRepository.existsMemberById(memberId);
    }

    // username 유저 존재하는지 확인하기
    private boolean existsMemberByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("멤버 없는데유?"));
    }
}
