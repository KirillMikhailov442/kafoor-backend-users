package dev.kafoor.users.dto.v1.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {
    private String name;
    private String email;
    private String nickname;

    @Override
    public String toString(){
        return "UserUpdate:" +
                "\n\tname:     " + name +
                "\n\temail:    " + email +
                "\n\tnickname: " + nickname;
    }
}
