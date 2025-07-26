package hello.squadfit.domain.record.entity;

import hello.squadfit.domain.user.entity.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "records")
public class Record {

    @Id @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id") // FK 이름
    private User user;

    private LocalDateTime recordDate;
    private Integer repeat;
    private Integer weight;
    private Integer successNumber;
    private Integer failNumber;

    @OneToOne
    @JoinColumn(name = "exercise_type_id")
    private ExerciseType exerciseType;
}
