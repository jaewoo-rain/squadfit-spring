package hello.squadfit.api;

import hello.squadfit.api.Member.request.CreateMemberProfileRequest;
import hello.squadfit.api.Member.request.CreateTrainerRequest;
import hello.squadfit.api.record.request.SaveRecordRequest;
import hello.squadfit.domain.member.service.AttendanceService;
import hello.squadfit.domain.member.service.MemberService;
import hello.squadfit.domain.member.service.TrainerService;
import hello.squadfit.domain.record.ExerciseCategory;
import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.record.repository.ExerciseTypeRepository;
import hello.squadfit.domain.record.service.RecordService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@Controller
@RequiredArgsConstructor
@Transactional
public class InitController {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final AttendanceService attendanceService;
    private final RecordService recordService;
    private final MemberService memberService;
    private final TrainerService trainerService;

    @PostConstruct
    public void init (){

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
        recordService.save(new SaveRecordRequest(member1Id,10,10,10,1, initType.getId()));
        recordService.save(new SaveRecordRequest(member1Id,20,20,20,2, initType.getId()));
        recordService.save(new SaveRecordRequest(member1Id,30,30,30,3, initType.getId()));
        recordService.save(new SaveRecordRequest(member1Id,40,30,30,3, initType.getId()));
        recordService.save(new SaveRecordRequest(member2Id,50,40,40,4, initType.getId()));

        // 출석 지정
        attendanceService.attendance(member1Id);
        attendanceService.attendance(member1Id);
        attendanceService.attendance(member1Id);
        attendanceService.attendance(member2Id);
        attendanceService.attendance(member2Id);
        attendanceService.attendance(member3Id);

        // 트레이너 지정
        Long trainer1Id = trainerService.register(new CreateTrainerRequest("initTrainer1", "1234", "110000", "010-1234-5678", "trainer1", "서울 체육관"));
        Long trainer2Id = trainerService.register(new CreateTrainerRequest("initTrainer2", "1234", "220000", "010-1234-5678", "trainer2", "전주 체육관"));
        Long trainer3Id = trainerService.register(new CreateTrainerRequest("initTrainer3", "1234", "330000", "010-1234-5678", "trainer3", "제주도 체육관"));


    }
}
