package kafoor.users.user_service.dtos;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeaderDTO {
    private String IP;
    private String userAgent;
}
