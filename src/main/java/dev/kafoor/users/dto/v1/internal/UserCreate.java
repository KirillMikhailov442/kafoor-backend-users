package dev.kafoor.users.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreate {
    private String name;
    private String email;
    private String nickname;
    private String password;

    @Override
    public String toString(){
        return "UserCreate:" +
                "\n\tname:         " + name +
                "\n\temail:        " + email +
                "\n\tnickname:     " + nickname;
    }
}
