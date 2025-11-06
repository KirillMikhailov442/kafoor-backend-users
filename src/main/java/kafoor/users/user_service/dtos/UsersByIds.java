package kafoor.users.user_service.dtos;

import java.util.List;

import lombok.Getter;

@Getter
public class UsersByIds {
    private List<Long> usersId;
}
