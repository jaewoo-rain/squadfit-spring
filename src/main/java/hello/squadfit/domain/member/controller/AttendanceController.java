package hello.squadfit.domain.member.controller;

import hello.squadfit.domain.member.response.AttendanceResponse;
import hello.squadfit.domain.member.service.AttendanceService;
import hello.squadfit.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Long> save(@PathVariable("memberId") Long memberId, @AuthenticationPrincipal CustomUserDetails userDetails){

        Long attendance = attendanceService.attendance(memberId);

        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<AttendanceResponse>> findByMember(@PathVariable("memberId") Long memberId){

        // todo: 서비스단에서 엔티티같은걸로 받아서 컨트롤러단에서 dto 생성해야할듯?
        List<AttendanceResponse> result = attendanceService.findAttendanceByMember(memberId);

        return ResponseEntity.ok(result);

    }
}
