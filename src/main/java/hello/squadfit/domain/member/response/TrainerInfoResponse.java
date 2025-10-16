package hello.squadfit.domain.member.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class TrainerInfoResponse {

    private String username;
    private String place;
    private String name;
    // todo: 코멘트들, 리포트들 추가할건가? 아니면 다른 api 사용할건가? 고민되네

}
