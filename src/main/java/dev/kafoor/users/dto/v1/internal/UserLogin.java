package dev.kafoor.users.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserLogin {
    private String email;
    private String password;

    @Override
    public String toString(){
        return "UserLogin:" +
                "\n\temail:    " + email +
                "\n\tpassword: " + password;
        }
    }
