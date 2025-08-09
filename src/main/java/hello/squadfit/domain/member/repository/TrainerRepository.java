package hello.squadfit.domain.member.repository;

import hello.squadfit.domain.member.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByProfileUsername(String username);

}
