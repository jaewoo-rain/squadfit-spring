package hello.squadfit.domain.member.dto;

import hello.squadfit.api.Member.request.CreateMemberRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemberDto {

    private String nickName;

    private String job;

    private Integer sedentary;

    private List<String> health;

    public static CreateMemberDto from(CreateMemberRequest request){

        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.nickName = request.getNickName();
        createMemberDto.job = request.getJob();
        createMemberDto.sedentary = request.getSedentary();
        createMemberDto.health = request.getHealth();

        return createMemberDto;
    }

}
