package hello.squadfit.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trainers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trainer {

    @Id @GeneratedValue
    @Column(name = "trainer_id")
    private Long id;

    @Embedded
    private UserProfile profile;

//    private List<Comment> comments;
//    private List<Report> reports;

    // == 생성 메서드 == //
    public static Trainer createTrainer(UserProfile profile){
        Trainer trainer = new Trainer();
        trainer.profile = profile;
        return trainer;
    }

    // == 비즈니스로직 ? == //
    /**
     * 프로필 변경
     */
    public void changeProfile(String name, String phone, String birth){
        profile.changeProfile(name, phone, birth);
    }
}
