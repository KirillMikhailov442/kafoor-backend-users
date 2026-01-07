package dev.kafoor.users.dto.v1.internal;

import dev.kafoor.users.dto.v1.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLogined {
    private UserResponse user;
    private String accessToken;
    private String refreshToken;

    @Override
    public String toString(){
        return "UserLogined:" +
                "\n\tuser:         " + user +
                "\n\taccessToken:  " + accessToken +
                "\n\trefreshToken: " + refreshToken;
    }
}
