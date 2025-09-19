package kafoor.users.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenCreateDTO {
    private String refresh;
    private String IP;
    private String userAgent;
}
