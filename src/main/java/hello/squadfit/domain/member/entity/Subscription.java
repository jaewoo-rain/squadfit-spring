package hello.squadfit.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Subscription {
    @Id @GeneratedValue
    @Column(name = "subscription_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // == 연관관계 편의 메서드 == //
    private void setMember(Member member){
        this.member = member;
        member.linkSubscription(this);
    }

    // == 생성 메서드 == //
    public static Subscription createSubscription(Member member, LocalDateTime startDate, LocalDateTime endDate){
        Subscription subscription = new Subscription();
        subscription.status = true;
        subscription.startDate = startDate;
        subscription.endDate = endDate;
        subscription.setMember(member);
        return subscription;
    }

    // == 비즈니스 로직 == //

    /**
     * 구독 해지
     */
    public void unsubscribe(){
        if(this.status == null && this.status == false){
            throw new IllegalStateException("구독한적이 없습니다.");
        }
        this.status = false;
        this.startDate = null;
        this.endDate = null;
    }

    /**
     * 구독 연장
     */

}
