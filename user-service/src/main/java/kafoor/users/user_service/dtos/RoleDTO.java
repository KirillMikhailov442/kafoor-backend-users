package kafoor.users.user_service.dtos;

import kafoor.users.user_service.models.enums.UserRoles;
import lombok.Getter;

@Getter
public class RoleDTO {
    private UserRoles role;
}
