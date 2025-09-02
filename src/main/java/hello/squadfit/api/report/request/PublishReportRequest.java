package hello.squadfit.api.report.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublishReportRequest {
    public String content;
    public String title;
}
