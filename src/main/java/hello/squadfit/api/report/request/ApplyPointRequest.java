package hello.squadfit.api.report.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public class ApplyPointRequest {

    private List<Long> videoIds;
    private Boolean isDetail;
}
