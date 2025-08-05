package hello.squadfit.domain.member.dto;

import hello.squadfit.domain.member.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CreateUserDto {
    private UserProfile profile;
    private String nickName;
}
