package hello.squadfit.api.Member.controller;

import hello.squadfit.domain.member.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Long> createSubscription(@PathVariable("memberId") Long memberId){
        Long subscriptionId = subscriptionService.createSubscription(memberId);

        return ResponseEntity.ok(subscriptionId);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<String> cancelSubscription(@PathVariable("memberId") Long memberId){
        subscriptionService.cancelSubscription(memberId);

        return ResponseEntity.ok("해제성공");
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<Long> extendSubscription(@PathVariable("memberId") Long memberId){
        Long subscriptionId = subscriptionService.extendSubscription(memberId);

        return ResponseEntity.ok(subscriptionId);
    }

}
