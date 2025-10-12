package hello.squadfit.api.Member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChangeUserRequest {

    @NotBlank
    private String birth;

}
