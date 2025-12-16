package dev.kafoor.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponse {
    private UserResponse user;
    private String accessToken;
    private String refreshToken;

    @Override
    public String toString(){
        return "LoginResponse:" +
                "\n\tuser:          " + user +
                "\n\taccessToken:  " + accessToken +
                "\n\trefreshToken: " + refreshToken;
    }
}
