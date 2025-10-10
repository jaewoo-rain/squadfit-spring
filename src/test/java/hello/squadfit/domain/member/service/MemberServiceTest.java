package hello.squadfit.domain.member.service;

import hello.squadfit.api.Member.request.CreateMemberRequest;
import hello.squadfit.api.Member.request.LoginRequest;
import hello.squadfit.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    public void register(){
        CreateMemberRequest createMemberProfileRequest =
                new CreateMemberRequest(
                        "test","1234","991111"
                        ,"010-1234-5678","jaewoo","yang");

        Long memberId = memberService.join(createMemberProfileRequest);
        Member findMember = memberService.findOne(memberId);

        assertThat(findMember.getNickName()).isEqualTo(createMemberProfileRequest.getNickName());

    }

    @Test
    public void login(){

        LoginRequest loginRequest = new LoginRequest("test", "1234");
//        Member member = memberService.login(loginRequest);

//        assertThat(member.getProfile().getUsername()).isEqualTo(loginRequest.getUsername());
//        assertThat(member.getProfile().getPassword()).isEqualTo(loginRequest.getPassword());

    }
  
}