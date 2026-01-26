package dev.kafoor.users.dto.v1.response;

import dev.kafoor.users.entity.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String nickname;
    private boolean isConfirmed;

    @Schema(
            description = "User roles",
            implementation = UserRole.class
    )
    private Set<UserRole> roles;

    @Override
    public String toString(){
        return "UserResponse:" +
                "\n\tid:          " + id +
                "\n\tname:        " + name +
                "\n\temail:       " + email +
                "\n\tnickname:    " + nickname +
                "\n\tisConfirmed: " + isConfirmed +
                "\n\troles:       " + roles;
    }
}
