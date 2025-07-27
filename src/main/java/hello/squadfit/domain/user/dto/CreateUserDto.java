package hello.squadfit.domain.user.dto;

import hello.squadfit.domain.user.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CreateUserDto {
    private UserProfile profile;
    private String nickName;
}
