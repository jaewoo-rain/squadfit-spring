package hello.squadfit.api.mission.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CreateMissionRequest {

    private String title;
    private String content;
}
