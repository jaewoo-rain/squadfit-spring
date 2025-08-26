package hello.squadfit.domain.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    // 레포트 발행하기?
    public void createReport(){

        // video 여러개 불러오기
        // video 이용해서 videoReport 만들기
        // videoReport 여러개 이용해서 report 만들기
        // report 저장하기
    }

    // 레포트 조회하기

    // 레포트 전제 조회하기(제목 정도만?)

    // 레포트 삭제하기

}
