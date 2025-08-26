package hello.squadfit.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    public void createReport(){

        // video 여러개 불러오기
        // video 이용해서 videoReport 만들기
        // videoReport 여러개 이용해서 report 만들기
        // report 저장하기
    }
}
