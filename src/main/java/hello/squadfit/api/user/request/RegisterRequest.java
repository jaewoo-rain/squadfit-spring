package hello.squadfit.api.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString(of = {""})
public class RegisterRequest {
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
