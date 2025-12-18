package dev.kafoor.users.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
                "\n\taccessToken:  " + accessToken +
                "\n\trefreshToken: " + refreshToken;
    }
}
