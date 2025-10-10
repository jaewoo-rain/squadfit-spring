package hello.squadfit.domain.member.repository;

import hello.squadfit.domain.member.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    List<Trainer> findAllByUserEntity_Name(String trainerName);

}
