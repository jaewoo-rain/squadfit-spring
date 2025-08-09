package hello.squadfit.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class ChangeProfileDto {
    @NotBlank
    private String birth;
    @NotBlank
    private String phone;
    @NotBlank
    private String name;
}
