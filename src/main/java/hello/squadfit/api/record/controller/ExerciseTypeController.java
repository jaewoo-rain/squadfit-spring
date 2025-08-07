package hello.squadfit.api.record.controller;

import hello.squadfit.domain.record.ExerciseCategory;
import hello.squadfit.domain.record.entity.ExerciseType;
import hello.squadfit.domain.record.repository.ExerciseTypeRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exercise-type")
public class ExerciseTypeController {

    private final ExerciseTypeRepository exerciseTypeRepository;

    @PostMapping
    public Long saveExercise(@RequestBody ExerciseDto exerciseDto){
        return exerciseTypeRepository.findByName(exerciseDto.getExerciseName())
                .map(exerciseType -> exerciseType.getId())
                .orElseGet(() -> {
                    ExerciseType newType = ExerciseType.createExerciseType(exerciseDto.getExerciseName(), exerciseDto.getCategory());
                    return exerciseTypeRepository.save(newType).getId();
                });
    }

    @GetMapping("{exerciseName}")
    public Long findExerciseType(@PathVariable String exerciseName){
        ExerciseType exerciseType = exerciseTypeRepository.findByName(exerciseName).orElseThrow(() -> new RuntimeException("운동이름 잘못됐는데?"));
        return exerciseType.getId();
    }

    @Getter
    public static class ExerciseDto{
        private String exerciseName;
        private ExerciseCategory category;
    }
}
