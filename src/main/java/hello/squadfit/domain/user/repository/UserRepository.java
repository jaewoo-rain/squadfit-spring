package hello.squadfit.domain.user.repository;

import hello.squadfit.domain.user.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByProfileUsername(String username);

}
