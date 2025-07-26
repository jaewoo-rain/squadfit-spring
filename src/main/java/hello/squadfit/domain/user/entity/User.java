package hello.squadfit.domain.user.entity;

import hello.squadfit.domain.payment.entity.Payment;
import hello.squadfit.domain.record.entity.BestRecord;
import hello.squadfit.domain.record.entity.Record;
import hello.squadfit.domain.report.entity.Report;
import hello.squadfit.domain.video.entity.Comment;
import hello.squadfit.domain.video.entity.Video;
import jakarta.persistence.*;
import lombok.Getter;

import javax.management.Notification;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private UserProfile profile;

    private String nickName;
    private int level;
    private Long requiredExperience; // 잔여 경험치
    private Boolean subscriptStatus; // 구독 여부

    // == 연관관계 ==

    private List<Notification> notifications = new ArrayList<>();

    private List<Payment> payments = new ArrayList<>();

    private List<Attendance> attendances = new ArrayList<>();

    private List<BestRecord> bestRecords = new ArrayList<>();

    @OneToMany(mappedBy = "user") // record 테이블에 있는 user 필드를 참조함
    private List<Record> records = new ArrayList<>();

    private List<Video> videos = new ArrayList<>();

    private List<Comment> comments = new ArrayList<>();

    private List<Report> reports = new ArrayList<>();

    // == 생성 메서드 ==
    public static User createUser(){

    }

}
