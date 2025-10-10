package hello.squadfit.domain.member.dto;

import hello.squadfit.api.Member.request.CreateUserRequest;
import lombok.*;

@Getter @Setter @Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserDto {

    private String username;
    private String password;
    private String birth;
    private String phone;
    private String name;

    public static <T extends CreateUserRequest> CreateUserDto from(T request) {

        return CreateUserDto.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .birth(request.getBirth())
                .phone(request.getPhone())
                .name(request.getName())
                .build();

    }

    public CreateUserDto encodePassword(String encodePassword){
        return CreateUserDto.builder()
                .username(this.username)
                .password(encodePassword)
                .birth(this.birth)
                .phone(this.phone)
                .name(this.name)
                .build();
    }
}