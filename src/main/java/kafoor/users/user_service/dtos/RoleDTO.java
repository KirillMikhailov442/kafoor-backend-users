package kafoor.users.user_service.dtos;

import kafoor.users.user_service.models.enums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private UserRoles role;
}
