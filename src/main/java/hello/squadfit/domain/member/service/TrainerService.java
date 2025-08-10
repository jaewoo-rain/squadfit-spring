package hello.squadfit.domain.member.service;

import hello.squadfit.api.Member.request.CreateTrainerRequest;
import hello.squadfit.api.Member.request.LoginRequest;
import hello.squadfit.domain.member.Role;
import hello.squadfit.domain.member.entity.Member;
import hello.squadfit.domain.member.entity.MemberProfile;
import hello.squadfit.domain.member.entity.Trainer;
import hello.squadfit.domain.member.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;

    @Transactional
    public Long register(CreateTrainerRequest request){

        
        if(trainerExist(request.getUsername())){
            throw new IllegalStateException("이미 가입되어있는 아이디입니다.");
        }

        MemberProfile memberProfile = MemberProfile.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .birth(request.getBirth())
                .phone(request.getPhone())
                .name(request.getName())
                .role(Role.Trainer)
                .build();

        Trainer trainer = Trainer.create(memberProfile, request.getPlace());

        trainerRepository.save(trainer);

        return trainer.getId();
    }

    // 로그인
    public Trainer login(LoginRequest loginRequest) {
        Trainer findTrainer = trainerRepository.findByProfileUsername(loginRequest.getUsername())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 아이디입니다."));

        if(!findTrainer.getProfile().getPassword().equals(loginRequest.getPassword())){
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        return findTrainer;
    }

    // 트레이너 관련 정보 받기 todo: 트레이너 정보 추가적으로 어떤거 넣을지 의논하기
    public Trainer findOneTrainer(Long trainerId){
        return trainerRepository.findById(trainerId).orElseThrow(() -> new RuntimeException("트레이너 없는데요?"));
    }
    // 트레이너 프로필 변경 todo: 트레이너 정보 추가될테니까 나중에하기


    // todo:페이징 적용하기
    public List<Trainer> findAllByName(String trainerName) {
        return trainerRepository.findAllByProfileName(trainerName);
    }


    // 아이디 이용해서 트레이너 기존에 존재하는지 확인하는 메서드
    private boolean trainerExist(String username) {
        return trainerRepository.existsTrainerByProfileUsername(username);
    }

}
