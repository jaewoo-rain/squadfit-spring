package hello.squadfit.domain.record.service;

import hello.squadfit.api.record.request.SaveRecordRequest;
import hello.squadfit.api.Member.response.AllRecordResponse;
import hello.squadfit.api.Member.response.SingleRecordResponse;
import hello.squadfit.domain.record.dto.CreateRecordDto;
import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.record.entity.ExerciseRecord;
import hello.squadfit.domain.record.repository.ExerciseTypeRepository;
import hello.squadfit.domain.record.repository.RecordRepository;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    public Long save(SaveRecordRequest request){

        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalStateException("유저 맞아?"));

        ExerciseType exerciseType = exerciseTypeRepository.findById(request.getExerciseTypeId())
                .orElseThrow(() -> new IllegalStateException("유저 종목 없는데?"));


        CreateRecordDto createRecordDto = CreateRecordDto.builder()
                .member(member)
                .repeat(request.getRepeatNumber())
                .weight(request.getWeight())
                .successNumber(request.getSuccessNumber())
                .failNumber(request.getFailNumber())
                .exerciseType(exerciseType)
                .build();

        ExerciseRecord exerciseRecord = ExerciseRecord.createRecord(createRecordDto);

        ExerciseRecord record = recordRepository.save(exerciseRecord);

        return record.getId();

    }

    // 전체 기록 조회
    public AllRecordResponse findAll(Long userId) {
        List<ExerciseRecord> all = recordRepository.findAllByMemberId(userId);
//        List<AllRecordResponse> result = all.stream().map(m ->
//                        AllRecordResponse.builder()
//                                .exerciseRecordId(m.getId())
//                                .exerciseName(m.getExerciseType().getName())
//                                .weight(m.getWeight())
//                                .repeatNumber(m.getRepeat())
//                                .volume(m.getWeight() * m.getRepeat())
//                                .successNumber(m.getSuccessNumber())
//                                .failNumber(m.getFailNumber())
//                                .category(m.getExerciseType().getCategory())
//                                .build())
//                .toList();

        List<SingleRecordResponse> list = all.stream().map(m -> SingleRecordResponse.builder()
                .exerciseRecordId(m.getId())
                .exerciseName(m.getExerciseType().getName())
                .weight(m.getWeight())
                .repeatNumber(m.getRepeat())
                .volume(m.getWeight() * m.getRepeat())
                .successNumber(m.getSuccessNumber())
                .failNumber(m.getFailNumber())
                .category(m.getExerciseType().getCategory())
                .build()).toList();

        return new AllRecordResponse(list);
    }

    // 단일 기록 조회
    public Optional<SingleRecordResponse> findOne(Long memberId, Long exerciseId) {
        Optional<ExerciseRecord> findRecord = recordRepository.findByMemberIdAndId(memberId, exerciseId);

        Optional<SingleRecordResponse> result = findRecord.map(record -> SingleRecordResponse.builder()
                .exerciseRecordId(record.getId())
                .exerciseName(record.getExerciseType().getName())
                .weight(record.getWeight())
                .repeatNumber(record.getRepeat())
                .volume(record.getWeight() * record.getRepeat())
                .successNumber(record.getSuccessNumber())
                .failNumber(record.getFailNumber())
                .category(record.getExerciseType().getCategory())
                .build()
        );

        return result;
    }

    // 기록 삭제
    public Long remove(Long exerciseId) {
        recordRepository.deleteById(exerciseId);
        return exerciseId;
    }
}
