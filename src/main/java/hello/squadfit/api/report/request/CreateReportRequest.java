package hello.squadfit.api.report.request;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateReportRequest {
    private List<Long> videoIds;
}

