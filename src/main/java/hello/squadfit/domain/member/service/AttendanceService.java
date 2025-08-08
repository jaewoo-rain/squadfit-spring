package hello.squadfit.domain.member.service;

import hello.squadfit.api.Member.response.AttendanceResponse;
import hello.squadfit.domain.member.entity.Attendance;
import hello.squadfit.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

    private final MemberService memberService;

    // 출석하기
    @Transactional
    public Long attendance(Long memberId){
        Member member = memberService.findOne(memberId).orElseThrow(() -> new RuntimeException("해당 멤버가 없습니다."));
        
        // 출석 하기 -> 변경 감지로 엔티티 저장 됨
        Attendance attendance = Attendance.create(member);

        // 경험치 및 포인트 증가
        member.increaseAttendancePoint();

        return attendance.getId();
    }

    // 출석 조회하기
    public List<AttendanceResponse> findAttendanceByMember(Long memberId){

        Member member = memberService.findOne(memberId).orElseThrow(() -> new RuntimeException("해당 멤버가 없습니다."));
        List<Attendance> attendances = member.getAttendances();

        return attendances.stream().map(attendance -> AttendanceResponse.builder()
                        .attendanceTime(attendance.getAttendanceTime())
                        .memberName(attendance.getMember().getNickName())
                        .build()
        ).toList();


    }
}
