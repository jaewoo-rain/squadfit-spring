package hello.squadfit.api;

import hello.squadfit.api.Member.request.CreateMemberProfileRequest;
import hello.squadfit.api.Member.request.CreateTrainerRequest;
import hello.squadfit.api.mission.request.CreateMissionRequest;
import hello.squadfit.api.record.request.SaveRecordRequest;
import hello.squadfit.api.report.request.PublishReportRequest;
import hello.squadfit.api.video.request.SaveVideoRequest;
import hello.squadfit.domain.member.service.AttendanceService;
import hello.squadfit.domain.member.service.MemberService;
import hello.squadfit.domain.member.service.TrainerService;
import hello.squadfit.domain.mission.service.MissionService;
import hello.squadfit.domain.record.ExerciseCategory;
import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.record.repository.ExerciseTypeRepository;
import hello.squadfit.domain.record.service.RecordService;
import hello.squadfit.domain.report.service.ReportService;
import hello.squadfit.domain.video.repository.VideoRepository;
import hello.squadfit.domain.video.service.CommentService;
import hello.squadfit.domain.video.service.VideoService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Controller
@RequiredArgsConstructor
@Transactional
public class InitController {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final AttendanceService attendanceService;
    private final RecordService recordService;
    private final MemberService memberService;
    private final TrainerService trainerService;
    private final VideoRepository videoRepository;
    private final VideoService videoService;
    private final ReportService reportService;
    private final EntityManager em;
    private final CommentService commentService;
    private final MissionService missionService;

    @PostConstruct
    public void init (){

        // 일일 미션 생성
        missionService.createMission(new CreateMissionRequest("첫번째 미션", "앉았다 일어나기"));



        // 타입 지정
        ExerciseType initType = exerciseTypeRepository.findByName("푸쉬업")
                .orElseGet(() -> {
                    ExerciseType newType = ExerciseType.createExerciseType("푸쉬업", ExerciseCategory.CHEST);
                    return exerciseTypeRepository.save(newType);
                });

        // 멤버 지정
        Long member1Id = memberService.register(new CreateMemberProfileRequest("init1", "1234", "001111", "010-1111-1111", "member1", "member1111"));
        Long member2Id = memberService.register(new CreateMemberProfileRequest("init2", "1234", "002222", "010-2222-2222", "member1", "member3333"));
        Long member3Id = memberService.register(new CreateMemberProfileRequest("init3", "1234", "003333", "010-3333-3333", "member1", "member3333"));

        // 기록 지정
        //member1, 10, 10, 10, 1, initType
        Long record1Id = recordService.save(new SaveRecordRequest(member1Id, 10, 10, 10, 1, initType.getId()));
        Long record2Id = recordService.save(new SaveRecordRequest(member1Id, 20, 20, 20, 2, initType.getId()));
        Long record3Id = recordService.save(new SaveRecordRequest(member1Id, 30, 30, 30, 3, initType.getId()));
        Long record4Id = recordService.save(new SaveRecordRequest(member2Id, 40, 30, 30, 3, initType.getId()));
        Long record5Id = recordService.save(new SaveRecordRequest(member2Id, 50, 40, 40, 4, initType.getId()));

        // 동영상 만들기
        SaveVideoRequest request1 = SaveVideoRequest.builder().title("1번 동영상").build();
        Long video1Id = videoService.saveByServer(member1Id, record1Id, request1);
        SaveVideoRequest request2 = SaveVideoRequest.builder().title("2번 동영상").build();
        Long video2Id = videoService.saveByServer(member1Id, record2Id, request2);
        SaveVideoRequest request3 = SaveVideoRequest.builder().title("3번 동영상").build();
        Long video3Id = videoService.saveByServer(member1Id, record3Id, request3);
        SaveVideoRequest request4 = SaveVideoRequest.builder().title("4번 동영상").build();
        Long video4Id = videoService.saveByServer(member2Id, record4Id, request4);
        SaveVideoRequest request5 = SaveVideoRequest.builder().title("5번 동영상").build();
        Long video5Id = videoService.saveByServer(member2Id, record5Id, request5);

        /**
         * 예외 멤버랑 기록의 매칭 잘못됨
         */
//        SaveVideoRequest request6 = SaveVideoRequest.builder().title("예외 동영상").visibility("PUBLIC").build();
//        Long l5 = videoService.saveByServer(member1Id, record5Id, request5);

        // 트레이너 지정
        Long trainer1Id = trainerService.register(new CreateTrainerRequest("initTrainer1", "1234", "110000", "010-1234-5678", "trainer1", "서울 체육관"));
        Long trainer2Id = trainerService.register(new CreateTrainerRequest("initTrainer2", "1234", "220000", "010-1234-5678", "trainer2", "전주 체육관"));
        Long trainer3Id = trainerService.register(new CreateTrainerRequest("initTrainer3", "1234", "330000", "010-1234-5678", "trainer3", "제주도 체육관"));

        // 레포트 신청하기
        Long report1Id = reportService.createReport(trainer1Id, member1Id, Arrays.asList(video1Id, video2Id));
        Long report2Id = reportService.createReport(trainer1Id, member1Id, Arrays.asList(video3Id));
        Long report3Id = reportService.createReport(trainer2Id, member2Id, Arrays.asList(video4Id));


        // 레포트 만들어주기
        PublishReportRequest reportRequest1 = PublishReportRequest.builder().title("레포트1번 작성지").content("레포트1번 내용").build();
        reportService.publishReport(report1Id, reportRequest1);
        /**
         * 예외 멤버랑 video랑 매칭 잘못 됨
         */
//        reportService.createReport(trainer3Id, member1Id, video4Id);

        // 출석 지정
        attendanceService.attendance(member1Id);
        attendanceService.attendance(member1Id);
        attendanceService.attendance(member1Id);
        attendanceService.attendance(member2Id);
        attendanceService.attendance(member2Id);
        attendanceService.attendance(member3Id);

        // 코멘트 신청하기
        commentService.requestComment(member1Id, video1Id);
        // 코멘트 작성하기
        commentService.createComment(video1Id, trainer1Id, "자세가 좋아요");

    }
}
