package hello.squadfit.domain.user.entity;

import hello.squadfit.domain.report.entity.Report;
import hello.squadfit.domain.video.entity.Comment;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Trainer {

    @Id @GeneratedValue
    @Column(name = "trainer_id")
    private Long id;

    @Embedded
    private UserProfile userProfile;

//    private List<Comment> comments;

//    private List<Report> reports;
}
