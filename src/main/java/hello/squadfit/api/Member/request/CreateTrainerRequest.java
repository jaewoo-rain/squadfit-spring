package hello.squadfit.api.Member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter @Builder
public class CreateTrainerRequest {
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
    private String place;
}
