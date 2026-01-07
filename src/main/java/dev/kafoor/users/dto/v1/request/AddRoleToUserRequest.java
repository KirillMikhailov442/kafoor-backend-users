package dev.kafoor.users.dto.v1.request;

import dev.kafoor.users.annotation.ValidEnumValue;
import dev.kafoor.users.entity.enums.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddRoleToUserRequest {
    @ValidEnumValue(message = "role must be a valid UserRole value")
    @NotNull(message = "role is required")
    private UserRole role;

    public String toString(){
        return "AddRoleToUserRequest: " +
                "\n\trole:  " + role.toString();
    }
}
