package hello.squadfit.domain.record.dto;

import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRecordDto {

    private User user;
    private Integer repeat;
    private Integer weight;
    private Integer successNumber;
    private Integer failNumber;
    private ExerciseType exerciseType;

}
