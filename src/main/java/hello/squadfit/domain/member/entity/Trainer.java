package hello.squadfit.domain.member.entity;

import hello.squadfit.domain.member.dto.ChangeProfileDto;
import hello.squadfit.domain.report.entity.Report;
import hello.squadfit.domain.video.entity.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity @Getter
@Table(name = "trainer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trainer {

    @Id @GeneratedValue
    @Column(name = "trainer_id")
    private Long id;

    @Embedded
    private MemberProfile profile;

    @Column
    private String place; // 체육관 장소

    // == 연관관계 == //
    @OneToMany(mappedBy = "trainer", cascade = ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "trainer", cascade = ALL)
    private List<Report> reports;

    // == 생성 메서드 == //
    public static Trainer create(MemberProfile profile, String place){
        Trainer trainer = new Trainer();
        trainer.profile = profile;
        trainer.place = place;

        return trainer;
    }

    // == 비즈니스로직 ? == //
    /**
     * 프로필 변경
     */
    public void changeProfile(String name, String phone, String birth){
        profile.changeProfile(ChangeProfileDto.builder()
                        .name(name)
                        .phone(phone)
                        .birth(birth)
                        .build()
        );
    }

}
