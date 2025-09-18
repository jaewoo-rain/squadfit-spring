package hello.squadfit.domain.report.entity;

import hello.squadfit.domain.video.entity.Video;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoPointReport {

    @Id
    @GeneratedValue
    @Column(name = "video_point_report")
    private Long id;

    // == 연관관계 == //
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "video_id")
    private Video video;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "point_report_id")
    private PointReport pointReport;

    // == 연관관계 편의 메서드 == //
    // == 생성 메서드 == //
    // == 비즈니스 로직 메서드 == //


}
