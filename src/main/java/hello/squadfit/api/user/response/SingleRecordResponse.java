package hello.squadfit.api.user.response;

import hello.squadfit.domain.record.ExerciseCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SingleRecordResponse {
    private Long exerciseRecordId;
    private String exerciseName;
    private Integer weight;
    private Integer repeatNumber;
    private Integer volume;
    private Integer successNumber;
    private Integer failNumber;
    private ExerciseCategory category;
}
