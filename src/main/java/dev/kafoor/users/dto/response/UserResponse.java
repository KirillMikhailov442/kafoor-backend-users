package dev.kafoor.users.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {
    private long id;
    private String name;
    private String email;
    private String nickname;
    private boolean isConfirmed;
    private List<String> roles;

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
