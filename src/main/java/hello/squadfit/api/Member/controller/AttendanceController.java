package hello.squadfit.api.Member.controller;

import hello.squadfit.api.Member.response.AttendanceResponse;
import hello.squadfit.domain.member.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/{memberId}")
    public ResponseEntity<Long> save(@PathVariable Long memberId){

        Long attendance = attendanceService.attendance(memberId);

        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<List<AttendanceResponse>> findByMember(@PathVariable Long memberId){
        List<AttendanceResponse> result = attendanceService.findAttendanceByMember(memberId);

        return ResponseEntity.ok(result);

    }
}
