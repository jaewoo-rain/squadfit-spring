package hello.squadfit.domain.user.entity;

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
    @JoinColumn(name = "user_id")
    private Users users;

    private Boolean status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    // == 연관관계 편의 메서드 == //
    private void setUsers(Users users){
        this.users = users;
        users.linkSubscription(this);
    }

    // == 생성 메서드 == //
    public static Subscription createSubscription(Users users, LocalDateTime startDate, LocalDateTime endDate){
        Subscription subscription = new Subscription();
        subscription.status = true;
        subscription.startDate = startDate;
        subscription.endDate = endDate;
        subscription.setUsers(users);
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
