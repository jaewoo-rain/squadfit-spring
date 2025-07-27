package hello.squadfit.domain.record.dto;

import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRecordDto {

    private User user;
    private int repeat;
    private int weight;
    private int successNumber;
    private int failNumber;
    private ExerciseType exerciseType;

}
