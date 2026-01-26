package dev.kafoor.users.dto.v1.internal;

import dev.kafoor.users.entity.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreated {
    private long id;
    private String name;
    private String email;
    private String nickname;
    private boolean isConfirmed;
    private Set<UserRole> roles;
    private String accessToken;
    private String refreshToken;

    @Override
    public String toString(){
        return "UserCreate:" +
                "\n\tid:           " + id +
                "\n\tname:         " + name +
                "\n\temail:        " + email +
                "\n\tnickname:     " + nickname +
                "\n\tisConfirmed:  " + isConfirmed +
                "\n\troles:        " + roles +
                "\n\taccessToken:  " + accessToken +
                "\n\trefreshToken: " + refreshToken;
    }
}
