package hello.squadfit.domain.record.entity;

import hello.squadfit.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "records")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    // == 연관관계 편의 메서드 == //
    private void setUser(User user){
        this.user = user;
        user.getRecords().add(this);
    }

    // == 생성 메서드 == //
    public static Record createRecord(User user, int repeat, int weight, int successNumber,
                                      int failNumber, ExerciseType exerciseType){
        Record record = new Record();
        record.setUser(user);
        record.recordDate = LocalDateTime.now();
        record.repeat = repeat;
        record.weight = weight;
        record.successNumber = successNumber;
        record.failNumber = failNumber;
        record.exerciseType = exerciseType;
        return record;
    }
}
