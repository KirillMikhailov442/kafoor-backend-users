package kafoor.users.user_service.dtos;

import kafoor.users.user_service.models.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCreateResDTO {
    private User user;
    private String access_token;
    private String refresh_token;
}
