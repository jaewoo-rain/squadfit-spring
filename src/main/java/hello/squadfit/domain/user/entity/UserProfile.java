package hello.squadfit.domain.user.entity;

import hello.squadfit.domain.user.Role;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class UserProfile {

    private String username;

    private String password;

    private String birth;

    private String phone;

    private String name;

    private Role role;

}
