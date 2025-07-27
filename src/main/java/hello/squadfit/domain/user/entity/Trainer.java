package hello.squadfit.domain.user.entity;

import hello.squadfit.domain.report.entity.Report;
import hello.squadfit.domain.video.entity.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "trainers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trainer {

    @Id @GeneratedValue
    @Column(name = "trainer_id")
    private Long id;

    @Embedded
    private UserProfile userProfile;

//    private List<Comment> comments;

//    private List<Report> reports;

    // == 생성 메서드 == //
    public static Trainer createTrainer(UserProfile userProfile){
        Trainer trainer = new Trainer();
        trainer.userProfile = userProfile;
        return trainer;
    }

    // == 비즈니스로직 ? == //

    /**
     * 프로필 변경
     */
    public void changeProfile(String name, String phone, String birth){
        userProfile.changeProfile(name, phone, birth);
    }
}
