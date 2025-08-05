package hello.squadfit.domain.member.dto;

import hello.squadfit.domain.member.entity.MemberProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CreateUserDto {
    private MemberProfile profile;
    private String nickName;
}
