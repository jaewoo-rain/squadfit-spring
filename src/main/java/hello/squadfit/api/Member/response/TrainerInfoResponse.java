package hello.squadfit.api.Member.response;

import lombok.*;

@Getter @Builder
public class TrainerInfoResponse {

    private String name;
    private String phone;

    private String place;
    // todo: 코멘트들, 리포트들 추가할건가? 아니면 다른 api 사용할건가? 고민되네

}
