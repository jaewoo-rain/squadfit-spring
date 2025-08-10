package hello.squadfit.domain.report.entity;

import hello.squadfit.domain.video.entity.Video;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class VideoReport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_report_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    // == 연관관계 == //
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "report_id")
    private Report report;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "video_id")
    private Video video;
}
