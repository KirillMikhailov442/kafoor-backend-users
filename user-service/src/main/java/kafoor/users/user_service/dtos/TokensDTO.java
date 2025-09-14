package kafoor.users.user_service.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokensDTO {
    private String accessToken;
    private String refreshToken;
}
