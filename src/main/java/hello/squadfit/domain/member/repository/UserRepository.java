package hello.squadfit.domain.member.repository;

import hello.squadfit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByProfileUsername(String username);
    boolean existsMemberById(Long memberId);

}
