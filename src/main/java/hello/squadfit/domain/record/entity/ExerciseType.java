package hello.squadfit.domain.record.entity;

import hello.squadfit.domain.record.ExerciseCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ExerciseType {

    @Id @GeneratedValue
    @Column(name = "exercise_type_id")
    private Long id;

    private String name;
    private ExerciseCategory category;

}
