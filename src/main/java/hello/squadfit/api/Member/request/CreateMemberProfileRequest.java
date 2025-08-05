package hello.squadfit.api.Member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CreateMemberProfileRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String birth;
    @NotBlank
    private String phone;
    @NotBlank
    private String name;
    @NotBlank
    private String nickName;

}
