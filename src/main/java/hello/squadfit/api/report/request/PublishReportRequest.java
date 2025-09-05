package hello.squadfit.api.report.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor
@Builder @NoArgsConstructor
public class PublishReportRequest {
    public String content;
    public String title;
}
