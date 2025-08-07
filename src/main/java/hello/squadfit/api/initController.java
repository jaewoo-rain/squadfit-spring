package hello.squadfit.api;

import hello.squadfit.domain.member.Role;
import hello.squadfit.domain.member.dto.CreateUserDto;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.entity.MemberProfile;
import hello.squadfit.domain.member.repository.MemberRepository;
import hello.squadfit.domain.record.ExerciseCategory;
import hello.squadfit.domain.record.dto.CreateRecordDto;
import hello.squadfit.domain.record.entity.ExerciseRecord;
import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.record.repository.ExerciseTypeRepository;
import hello.squadfit.domain.record.repository.RecordRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class initController {

    private final ExerciseTypeRepository exerciseTypeRepository;
    private final MemberRepository memberRepository;
    private final RecordRepository recordRepository;

    @PostConstruct
    public void init (){
        // 타입 지정
        ExerciseType initType = exerciseTypeRepository.findByName("푸쉬업")
                .orElseGet(() -> {
                    ExerciseType newType = ExerciseType.createExerciseType("푸쉬업", ExerciseCategory.CHEST);
                    return exerciseTypeRepository.save(newType);
                });

        // 멤버 지정
        Member member1 = Member.createUser(new CreateUserDto(new MemberProfile("init1", "1234", "001111", "010-1111-111", "member1", Role.Member), "member1111"));
        Member member2 = Member.createUser(new CreateUserDto(new MemberProfile("init2", "1234", "002222", "010-2222-2222", "member2", Role.Member), "member2222"));
        Member member3 = Member.createUser(new CreateUserDto(new MemberProfile("init3", "1234", "003333", "010-3333-3333", "member3", Role.Member), "member3333"));

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        // 기록 지정
        //member1, 10, 10, 10, 1, initType
        ExerciseRecord record1 = ExerciseRecord.createRecord(CreateRecordDto.builder().member(member1).repeat(10).weight(10).successNumber(10).failNumber(1).exerciseType(initType).build());
        ExerciseRecord record2 = ExerciseRecord.createRecord(CreateRecordDto.builder().member(member1).repeat(20).weight(20).successNumber(20).failNumber(2).exerciseType(initType).build());
        ExerciseRecord record3 = ExerciseRecord.createRecord(CreateRecordDto.builder().member(member2).repeat(30).weight(30).successNumber(30).failNumber(3).exerciseType(initType).build());

        recordRepository.save(record1);
        recordRepository.save(record2);
        recordRepository.save(record3);

    }
}
