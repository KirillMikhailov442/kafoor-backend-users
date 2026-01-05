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
public class TokensResponse {
    private String accessToken;
    private String refreshToken;

    @Override
    public String toString(){
        return "TokensResponse" +
                "\n\taccessToken:   " + accessToken +
                "\n\trefreshToken:  " + refreshToken;
    }
}
