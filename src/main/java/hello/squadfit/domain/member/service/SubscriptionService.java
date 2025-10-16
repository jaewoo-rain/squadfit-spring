package hello.squadfit.domain.member.service;

import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.entity.Subscription;
import hello.squadfit.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {

    private final MemberService memberService;

    // 구독하기
    public Long createSubscription(Long memberId){

        Member findMember = memberService.findOne(memberId);

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(1);

        return findMember.subscribe(startDate, endDate);

    }

    // 해제하기
    public Long cancelSubscription(Long memberId){

        Member findMember = memberService.findOne(memberId);

        findMember.cancelSubscription();

        return memberId;
    }

    // 연장하기
    public Long extendSubscription(Long memberId){
        Member findMember = memberService.findOne(memberId);

        Subscription subscription = findMember.getSubscription();

        LocalDateTime originalEndDate = subscription.getEndDate();
        LocalDateTime endDate = originalEndDate.plusMonths(1);

        return findMember.extendSubscription(endDate);
    }

}
