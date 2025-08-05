package hello.squadfit.api.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AllRecordResponse {

    List<SingleRecordResponse> recordResponseList;

}
