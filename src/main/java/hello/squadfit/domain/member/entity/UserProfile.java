package hello.squadfit.domain.member.entity;

import hello.squadfit.domain.member.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {
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
    private Role role;

    public void changeProfile(String name, String phone, String birth){
        this.birth = birth;
        this.phone = phone;
        this.name = name;
    }

    @Builder
    public UserProfile(String username, String password, String birth, String phone, String name, Role role){
        this.username = username;
        this.password = password;
        this.birth = birth;
        this.phone = phone;
        this.name = name;
        this.role = role;
    }

}
