package hello.squadfit.domain.member.entity;

import hello.squadfit.domain.member.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 바귀지 않는 member, trainer 정보
public class MemberProfile {
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public void changeProfile(String name, String phone, String birth){
        this.birth = birth;
        this.phone = phone;
        this.name = name;
    }

    @Builder
    public MemberProfile(String username, String password, String birth, String phone, String name, Role role){
        this.username = username;
        this.password = password;
        this.birth = birth;
        this.phone = phone;
        this.name = name;
        this.role = role;
    }

}
