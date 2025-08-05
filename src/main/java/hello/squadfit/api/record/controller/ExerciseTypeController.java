package hello.squadfit.api.record.controller;

import hello.squadfit.domain.record.ExerciseCategory;
import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.record.repository.ExerciseTypeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercise-type")
public class ExerciseTypeController {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @PostMapping
    public Long saveExercise(@RequestBody ExerciseDto exerciseDto){
        ExerciseType exerciseType = ExerciseType.createExerciseType(exerciseDto.getExerciseName(), exerciseDto.getCategory());
        ExerciseType type = exerciseTypeRepository.save(exerciseType);
        return type.getId();
    }

    @Getter
    public static class ExerciseDto{
        private String exerciseName;
        private ExerciseCategory category;
    }
}
