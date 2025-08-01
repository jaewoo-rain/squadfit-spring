package hello.squadfit.domain.user.repository;

import hello.squadfit.domain.user.Role;
import hello.squadfit.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserJpaRepository {

    private final EntityManager em;

    // 저장
    public Long save(User user){
        em.persist(user);
        return user.getId();
    }

    // 아이디로 유저 찾기
    public User findOne(Long id){
        return em.find(User.class, id);
    }

    // 유저아이디로 유저 찾기
    public List<User> findByUsername(String username){
        return em.createQuery("select u from User u where u.profile.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<User> findAll(){
        return em.createQuery("select u from User u ", User.class)
                .getResultList();
    }

    // 닉네임으로 유저 찾기
    public List<User> findByName(String nickName){
        return em.createQuery("select u from User u where u.nickName = :nickName", User.class)
                .setParameter("nickName", nickName)
                .getResultList();
    }

//    public User findByRole(Role role){
//        em.createQuery("select u from User u where u.ro")
//    }

}
