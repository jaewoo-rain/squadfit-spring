package hello.squadfit.domain.member.service;

import hello.squadfit.api.Member.request.CreateTrainerRequest;
import hello.squadfit.api.Member.request.LoginRequest;
import hello.squadfit.domain.member.Role;
import hello.squadfit.domain.member.entity.MemberProfile;
import hello.squadfit.domain.member.entity.Trainer;
import hello.squadfit.domain.member.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class TrainerService {

    private final TrainerRepository trainerRepository;

    @Transactional
    public Long register(CreateTrainerRequest request){

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
}
