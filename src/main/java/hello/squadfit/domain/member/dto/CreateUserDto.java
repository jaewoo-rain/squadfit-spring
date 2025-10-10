package hello.squadfit.domain.member.dto;

import hello.squadfit.api.Member.request.CreateUserRequest;
import lombok.*;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserDto {

    private String username;
    private String password;
    private String birth;
    private String phone;
    private String name;

    public static <T extends CreateUserRequest> CreateUserDto from(T request) {

        CreateUserDto dto = new CreateUserDto();
        dto.username = request.getUsername();
        dto.password = request.getPassword();
        dto.birth = request.getBirth();
        dto.phone = request.getPhone();
        dto.name = request.getName();

        return dto;
    }
}