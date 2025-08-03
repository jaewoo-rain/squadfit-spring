package hello.squadfit.domain.record.repository;

import hello.squadfit.domain.record.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {
}
