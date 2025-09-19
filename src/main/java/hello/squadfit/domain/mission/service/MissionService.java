package hello.squadfit.domain.mission.service;

import hello.squadfit.api.mission.request.CreateMissionRequest;
import hello.squadfit.api.mission.response.MissionResponse;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.service.MemberService;
import hello.squadfit.domain.mission.entity.Mission;
import hello.squadfit.domain.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.random;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionRepository missionRepository;
    private final MemberService memberService;

    // 미션 만들기
    public Long createMission(CreateMissionRequest request){
        Mission mission = Mission.create(request.getContent(), request.getTitle());
        Mission save = missionRepository.save(mission);

        return save.getId();
    }

    // 아이디로 미션 확인
    public MissionResponse findMissionById(Long missionId){
        Mission mission = missionRepository.findById(missionId).orElseThrow(() -> new RuntimeException("미션 없습니다."));

        return MissionResponse.entityToResponse(mission);
    }

    // 일일 미션 불러오기 (0일차부터 31일차까지 미리 세팅해야할듯)
    public MissionResponse mission(Long memberId){

        //todo: 출석과 미션은 분리시킨다.
        //todo: 레포트에 포인트 사용함, 일정 포인트 -> 현금화, 포인트 이용하여 매일매일 정밀 간단 레포트 (토큰수에 따라서 500포인트 -> 1000포인트)
        //todo: 코멘트 포인트는 이후에 도입함, 포인트 사용하면 정밀 레포트(5만) 받을 수 있음
        //todo: 일일미션 -> 스트레칭 처럼 간단하게(한달에 한번씩 바뀜)
        //todo: 운동시간추가하기, 출석 시 간단히 입력창?
        Member findMember = memberService.findOne(memberId);
        Long missionCount = findMember.getMissionCount();

        Mission findMission = getMission(missionCount);

        return MissionResponse.entityToResponse(findMission);
    }

    // 미션 성공
    // 한달 시 미션 완료 여부에 따른 답장 해주기, 미션 성공 횟수 초기화
    public Long successMission(Long memberId){
        Member findMember = memberService.findOne(memberId);
        
        findMember.successMission();
        Long missionCount = findMember.getMissionCount();
        // todo: 하루에 여러번 미션 성공 못하게 로직 작성하기
        // todo: 가입날짜 만들어서 가입날짜 기준 한달 지난 뒤면 상,중,하 리턴
        int createDay = findMember.getCreatedDate().getDayOfMonth();
        log.info("createDay = {}", createDay);

        return missionCount;

    }


    public Mission getMission(Long missionId){
        return missionRepository.findById(missionId).orElseThrow(() -> new RuntimeException("미션 없습니다."));
    }

}
