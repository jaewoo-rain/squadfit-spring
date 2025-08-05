package hello.squadfit.domain.record.entity;

import hello.squadfit.domain.record.ExerciseCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseType {

    @Id @GeneratedValue
    @Column(name = "exercise_type_id")
    private Long id;

    private String name;
    private ExerciseCategory category;

    public static ExerciseType createExerciseType(String name, ExerciseCategory category){
        ExerciseType exerciseType = new ExerciseType();
        exerciseType.name = name;
        exerciseType.category = category;
        return exerciseType;
    }

}
