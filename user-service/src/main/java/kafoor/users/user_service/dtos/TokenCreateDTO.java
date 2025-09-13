package kafoor.users.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenCreateDTO {
    private String refresh;
    private String IP;
    private String userAgent;
}
