package kafoor.users.user_service.dtos;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokensDTO {
    private String accessToken;
    private String refreshToken;
}
