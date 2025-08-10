package hello.squadfit.domain.report.entity;

import hello.squadfit.domain.member.entity.Trainer;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;
    private String title;
    private String content;
    // todo: 파일로 추가할 예정 지금은 제목과 내용으로 간단히 만들기

    // == 연관관계 == //
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @OneToMany(mappedBy = "report", cascade = ALL, orphanRemoval = true)
    private List<VideoReport> videoReports;
}
