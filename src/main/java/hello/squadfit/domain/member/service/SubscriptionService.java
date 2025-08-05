package hello.squadfit.domain.member.service;

import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionService {

    private final MemberRepository memberRepository;

    // 구독하기
    public Long createSubscription(Long memberId){

        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("사람 없어유"));
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(1);

        Long subscriptionId = findMember.subscribe(startDate, endDate);

        return subscriptionId;

    }

    // 해제하기
    public void cancelSubscription(Long memberId){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("사람이 맞아요?"));
        findMember.cancelSubscription();
    }

    // 연장하기
    public Long extendSubscription(Long memberId){
        Member findMember = memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("사람이 맞아요?"));
        LocalDateTime originalEndDate = findMember.getSubscription().getEndDate();
        LocalDateTime endDate = originalEndDate.plusMonths(1);
        Long subscriptionId = findMember.extendSubscription(endDate);

        return subscriptionId;
    }

}
