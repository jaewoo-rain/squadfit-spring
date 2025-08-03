package hello.squadfit.api.record.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SaveRecordRequest {

    private Long userId;
    private Integer weight;
    private Integer repeatNumber;
    private Integer successNumber;
    private Integer failNumber;
    private Long exerciseTypeId;

}
