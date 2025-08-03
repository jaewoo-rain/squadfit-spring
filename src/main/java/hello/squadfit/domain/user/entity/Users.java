package hello.squadfit.domain.user.entity;

import hello.squadfit.domain.PointConst;
import hello.squadfit.domain.record.entity.ExerciseRecord;
import hello.squadfit.domain.user.dto.CreateUserDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
//@Builder // 나중에 도입하자
//@AllArgsConstructor(access = AccessLevel.PRIVATE) // Builder때문에 사용해야함, 생성자 외부에서 못만들도록 하기 위해
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자 protected로 설정하여 기본생성자 사용못하게 막기
@Table(name = "users")
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private UserProfile profile;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    private Integer requiredExperience; // 잔여 경험치

//    private Boolean subscribed; // 구독 여부
    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Integer availableReportCount; // 레포트 신청 가능한 숫자

    // == 연관관계 ==
    @OneToOne(mappedBy = "users", fetch = LAZY)
    private Subscription subscription;

//    private List<Notification> notifications = new ArrayList<>();

//    private List<Payment> payments = new ArrayList<>();

//    private List<Attendance> attendances = new ArrayList<>();

//    private List<BestRecord> bestRecords = new ArrayList<>();

    @OneToMany(mappedBy = "users") // record 테이블에 있는 user 필드를 참조함
    private List<ExerciseRecord> exerciseRecords = new ArrayList<>();

//    private List<Video> videos = new ArrayList<>();

//    private List<Comment> comments = new ArrayList<>();

//    private List<Report> reports = new ArrayList<>();

    // == 연관관계 편의 메서드 == //
    public void linkSubscription(Subscription subscription){
        this.subscription = subscription;
    }


    // == 생성 메서드 == //
    public static Users createUser(CreateUserDto dto){
        Users users = new Users();
        users.profile = dto.getProfile();
        users.nickName = dto.getNickName();
        users.level = 1;
        users.requiredExperience = 100;
        users.subscription = null;
        users.point = 0;
        users.availableReportCount = 0;
        return users;
    }

    // == 비즈니스 로직 == //
    /**
     * 출석 포인트 증가
     */
    public void attendancePoint(){
        gainExperience(PointConst.ATTENDANCE_POINT);
        point += PointConst.ATTENDANCE_POINT;
    }

    /**
     * 운동하기 포인트 증가
     */
    public void exercisePoint(){
        gainExperience(PointConst.EXERCISE_POINT);
        point += PointConst.EXERCISE_POINT;
    }

    /**
     * 코멘트 달기 포인트 감소
     */
    public void requestComment(){
        if(point < PointConst.COMMENT_POINT){
            throw new IllegalStateException("포인트가 부족하여 코멘트 신청이 불가합니다");
        }
        point -= PointConst.COMMENT_POINT;
    }

    /**
     * 레포트 신청하기
     */
    public void requestReport() {
        if (availableReportCount <= 0) {
            throw new IllegalStateException("신청 가능한 레포트 수가 없습니다.");
        }
        availableReportCount--;
    }

    /**
     * 구독하기
     */
    public void subscribe(LocalDateTime startDate, LocalDateTime endDate){
        if (this.subscription != null && Boolean.TRUE.equals(this.subscription.getStatus())) {
            throw new IllegalStateException("이미 구독 중입니다.");
        }
        this.subscription = Subscription.createSubscription(this, startDate, endDate);
    }
    /**
     * 구독해지
     */
    public void unsubscribe(){
        if (this.subscription == null) {
            throw new IllegalStateException("구독 정보가 없습니다.");
        }
        subscription.unsubscribe();
    }

    /**
     * 닉네임 변경
     */
    public void changeNickName(String nickName){
        this.nickName = nickName;
    }

    /**
     * 프로필 변경
     */
    public void changeProfile(String name, String phone, String birth){
        profile.changeProfile(name, phone, birth);

    }

    // 경험치 획득
    private void gainExperience(Integer exp){

        int remainExp = requiredExperience - exp;
        if(remainExp <= 0){
            levelUp(remainExp);

        }else{
            // 레벨업 못함
            requiredExperience = remainExp;
        }

    }

    // 레벨 업
    private void levelUp(int remainExp) {
        // 레벨업
        level++;
        // 새로운 필요 경험치
        requiredExperience = PointConst.INITIAL_EXPERIENCE + (level - 1) * PointConst.LEVEL_UP_POINT;
        // 남은 경험치를 다음 레벨의 필요 경험치에서 차감
        requiredExperience += remainExp;
    }
}
