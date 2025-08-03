package hello.squadfit.api.user.response;

import lombok.*;

@Getter
//@NoArgsConstructor
@Builder
public class LoginResponse {

    private String nickName;
    private Integer level;
    private Integer requiredExperience; // 잔여 경험치
    private Integer point;
    private Integer availableReportCount; // 레포트 신청 가능한 숫자

}
