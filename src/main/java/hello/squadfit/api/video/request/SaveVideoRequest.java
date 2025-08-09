package hello.squadfit.api.video.request;

import hello.squadfit.domain.video.entity.VideoVisibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class SaveVideoRequest {

    @NotBlank
    private String title;
    @NotNull
    private VideoVisibility visibility;

}
