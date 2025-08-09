package hello.squadfit.domain.record.service;

import hello.squadfit.api.Member.response.mapper.SingleRecordResponseMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecordService {

    private final RecordRepository recordRepository;
    private final MemberRepository memberRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    // 기록 저장하기
    @Transactional
    public Long save(SaveRecordRequest request){

        // 유효성 검사
        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalStateException("유저 맞아?"));

        ExerciseType exerciseType = exerciseTypeRepository.findById(request.getExerciseTypeId())
                .orElseThrow(() -> new IllegalStateException("유저 종목 없는데?"));

        // DTO 변경
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

        // 포인트 증가
        member.increaseExercisePoint();

        return record.getId();

    }

    // 전체 기록 조회
    // TODO: 페이징 처리하기
    public AllRecordResponse findAll(Long userId) {
        List<ExerciseRecord> all = recordRepository.findAllByMemberId(userId);

        List<SingleRecordResponse> list = all.stream()
                .map(record -> SingleRecordResponseMapper.entityToDto(record)).toList();

        return new AllRecordResponse(list);
    }

    // 단일 기록 조회
    public Optional<SingleRecordResponse> findOne(Long memberId, Long recordId) {
        Optional<ExerciseRecord> findRecord = recordRepository.findByMemberIdAndId(memberId, recordId);

        Optional<SingleRecordResponse> result = findRecord
                .map(record -> SingleRecordResponseMapper.entityToDto(record));

        return result;
    }

    public Optional<ExerciseRecord> getRecord(Long recordId){
        return recordRepository.findById(recordId);
    }

    // 기록 삭제
    @Transactional
    public Long remove(Long exerciseId) {
        recordRepository.deleteById(exerciseId);
        return exerciseId;
    }
}
