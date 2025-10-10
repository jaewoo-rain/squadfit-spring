package hello.squadfit.domain.member.dto;

import hello.squadfit.api.Member.request.CreateMemberRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemberDto {

    private String nickName;

    public static CreateMemberDto from(CreateMemberRequest request){

        CreateMemberDto createMemberDto = new CreateMemberDto();
        createMemberDto.nickName = request.getNickName();
        return createMemberDto;

    }

}
