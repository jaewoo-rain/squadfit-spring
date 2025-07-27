package hello.squadfit.api.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CreateUserProfileRequest {

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

}
