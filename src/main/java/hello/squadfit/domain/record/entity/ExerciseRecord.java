package hello.squadfit.domain.record.entity;

import hello.squadfit.domain.record.dto.CreateRecordDto;
import hello.squadfit.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@Entity @Getter
@Table(name = "records")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExerciseRecord {

    @Id @GeneratedValue
    @Column(name = "record_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id") // FK 이름
    private Member member;

    private LocalDateTime recordDate;
    private Integer repeat;
    private Integer weight;
    private Integer successNumber;
    private Integer failNumber;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exercise_type_id")
    private ExerciseType exerciseType;

    // == 연관관계 편의 메서드 == //
    private void setMember(Member member){
        this.member = member;
        member.getExerciseRecords().add(this);
    }

    // == 생성 메서드 == //
    public static ExerciseRecord createRecord(CreateRecordDto createRecordDto){
        ExerciseRecord exerciseRecord = new ExerciseRecord();
        exerciseRecord.setMember(createRecordDto.getMember());
        exerciseRecord.recordDate = LocalDateTime.now();
        exerciseRecord.repeat = createRecordDto.getRepeat();
        exerciseRecord.weight = createRecordDto.getWeight();
        exerciseRecord.successNumber = createRecordDto.getSuccessNumber();
        exerciseRecord.failNumber = createRecordDto.getFailNumber();
        exerciseRecord.exerciseType = createRecordDto.getExerciseType();
        return exerciseRecord;
    }
}
