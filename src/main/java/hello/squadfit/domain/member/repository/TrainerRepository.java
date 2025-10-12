package hello.squadfit.domain.member.repository;

import hello.squadfit.domain.member.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    List<Trainer> findAllByName(String trainerName);

}
