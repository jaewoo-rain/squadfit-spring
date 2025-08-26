package hello.squadfit.domain.report.service;

import hello.squadfit.api.report.request.PublishReportRequest;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.entity.Trainer;
import hello.squadfit.domain.member.service.MemberService;
import hello.squadfit.domain.member.service.TrainerService;
import hello.squadfit.domain.report.entity.Report;
import hello.squadfit.domain.report.entity.VideoReport;
import hello.squadfit.domain.report.repository.ReportRepository;
import hello.squadfit.domain.video.entity.Video;
import hello.squadfit.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {


    private final MemberService memberService;
    private final VideoService videoService;
    private final TrainerService trainerService;
    private final ReportRepository reportRepository;


    // member 가 레포트 신청하기
    public void createReport(Long trainerId, Long memberId, Long videoId){
        //todo: 이 비디오가 멤버꺼 맞는지 확인

        Member member = memberService.findOne(memberId).orElseThrow(() -> new RuntimeException("member 없는데요?"));
        Video video = videoService.findOne(videoId);
        Trainer trainer = trainerService.findOneTrainer(trainerId);

        VideoReport videoReport = VideoReport.create(video);

        Report.create(trainer, member, videoReport);

        // video 여러개 불러오기
        // video 이용해서 videoReport 만들기
        // videoReport 여러개 이용해서 report 만들기
        // report 저장하기
    }

    // 레포트 작성하기
    public void publishReport(Long reportId, PublishReportRequest request){
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("레포트 없는데?"));
        report.publishReport(request.getContent(), request.getTitle());
    }

    // 레포트 조회하기

    // 레포트 전제 조회하기(제목 정도만?)

    // 레포트 삭제하기

}
