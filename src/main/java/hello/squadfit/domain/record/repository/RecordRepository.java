package hello.squadfit.domain.record.repository;

import hello.squadfit.domain.record.entity.ExerciseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<ExerciseRecord, Long> {

    List<ExerciseRecord> findAllByUsersId(Long userId);

}
