package hello.squadfit.domain.member.entity;

import hello.squadfit.domain.PointConst;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class Point {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private Integer mount;


//    /**
//     * 출석 포인트 증가
//     */
//    public void attendancePoint(){
//        gainExperience(PointConst.ATTENDANCE_POINT);
//        point += PointConst.ATTENDANCE_POINT;
//    }
//
//    /**
//     * 운동하기 포인트 증가
//     */
//    public void exercisePoint(){
//        gainExperience(PointConst.EXERCISE_POINT);
//        point += PointConst.EXERCISE_POINT;
//    }
//
//    /**
//     * 코멘트 달기 포인트 감소
//     */
//    public void requestComment(){
//        if(point < PointConst.COMMENT_POINT){
//            throw new IllegalStateException("포인트가 부족하여 코멘트 신청이 불가합니다");
//        }
//        point -= PointConst.COMMENT_POINT;
//    }

}
