package hello.squadfit.api.Member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter @Builder
public class AttendanceResponse {

    private LocalDateTime attendanceTime;
    private String memberName;

}
