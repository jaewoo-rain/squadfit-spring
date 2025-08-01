package hello.squadfit.domain.record.entity;

import hello.squadfit.domain.record.dto.CreateRecordDto;
import hello.squadfit.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "records")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Record {

    @Id @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "user_id") // FK 이름
    private User user;

    private LocalDateTime recordDate;
    private Integer repeat;
    private Integer weight;
    private Integer successNumber;
    private Integer failNumber;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "exercise_type_id")
    private ExerciseType exerciseType;

    // == 연관관계 편의 메서드 == //
    private void setUser(User user){
        this.user = user;
        user.getRecords().add(this);
    }

    // == 생성 메서드 == //
    public static Record createRecord(CreateRecordDto createRecordDto){
        Record record = new Record();
        record.setUser(createRecordDto.getUser());
        record.recordDate = LocalDateTime.now();
        record.repeat = createRecordDto.getRepeat();
        record.weight = createRecordDto.getWeight();
        record.successNumber = createRecordDto.getSuccessNumber();
        record.failNumber = createRecordDto.getFailNumber();
        record.exerciseType = createRecordDto.getExerciseType();
        return record;
    }
}
