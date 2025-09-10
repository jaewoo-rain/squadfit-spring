package hello.squadfit.api.mission.controller;

import hello.squadfit.api.mission.response.MissionResponse;
import hello.squadfit.domain.mission.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mission")
public class MissionController {

    private final MissionService missionService;

    // 미션 성공
    @PutMapping("/{memberId}")
    public ResponseEntity<Long> successMission(@PathVariable("memberId") Long memberId){

        Long successMission = missionService.successMission(memberId);

        return ResponseEntity.ok(successMission);

    }

    // 미션 받아오기
    @GetMapping("/{memberId}")
    public ResponseEntity<MissionResponse> getMission(@PathVariable("memberId") Long memberId){
        MissionResponse mission = missionService.mission(memberId);

        return ResponseEntity.ok(mission);
    }

}
