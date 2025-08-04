package hello.squadfit.domain.record.service;

import hello.squadfit.api.record.request.SaveRecordRequest;
import hello.squadfit.api.user.response.SingleRecordResponse;
import hello.squadfit.domain.record.dto.CreateRecordDto;
import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.record.entity.ExerciseRecord;
import hello.squadfit.domain.record.repository.ExerciseTypeRepository;
import hello.squadfit.domain.record.repository.RecordRepository;
import hello.squadfit.domain.user.entity.Users;
import hello.squadfit.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    public Long save(SaveRecordRequest request){

        Users users = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalStateException("유저 맞아?"));

        ExerciseType exerciseType = exerciseTypeRepository.findById(request.getExerciseTypeId())
                .orElseThrow(() -> new IllegalStateException("유저 종목 없는데?"));


        CreateRecordDto createRecordDto = CreateRecordDto.builder()
                .users(users)
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

    public List<SingleRecordResponse> findAll(Long userId) {
        List<ExerciseRecord> all = recordRepository.findAllByUsersId(userId);
        List<SingleRecordResponse> result = all.stream().map(m ->
                        SingleRecordResponse.builder()
                                .exerciseRecordId(m.getId())
                                .exerciseName(m.getExerciseType().getName())
                                .weight(m.getWeight())
                                .repeatNumber(m.getRepeat())
                                .volume(m.getWeight() * m.getRepeat())
                                .successNumber(m.getSuccessNumber())
                                .failNumber(m.getFailNumber())
                                .category(m.getExerciseType().getCategory())
                                .build())
                .toList();
        return result;
    }

}
