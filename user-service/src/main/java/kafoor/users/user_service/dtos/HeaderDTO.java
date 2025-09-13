package kafoor.users.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HeaderDTO {
    private String IP;
    private String userAgent;
}
