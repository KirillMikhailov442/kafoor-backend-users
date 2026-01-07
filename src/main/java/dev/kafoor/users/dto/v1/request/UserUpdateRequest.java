package dev.kafoor.users.dto.v1.request;

import dev.kafoor.users.constant.EmailConstants;
import dev.kafoor.users.constant.NicknameConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequest {
    @NotBlank(message = "name is mandatory")
    @Size(min = 2, max = 32, message = "name length should be 2 to 32 characters")
    private String name;

    @NotBlank(message = "email is mandatory")
    @Email(message = "incorrect email")
    @Size(min = EmailConstants.MIN_LENGTH_EMAIL, max = EmailConstants.MAX_LENGTH_EMAIL, message = "email length should be" + EmailConstants.MIN_LENGTH_EMAIL + "to" + EmailConstants.MAX_LENGTH_EMAIL + "characters")
    private String email;

    @NotBlank(message = "nickname is mandatory")
    @Pattern(regexp = NicknameConstants.REGEXP_NICKNAME, message = "the nickname should start with @")
    @Size(min = NicknameConstants.MIN_LENGTH_NICKNAME, max = NicknameConstants.MAX_LENGTH_NICKNAME, message = "nickname length should be " + NicknameConstants.MIN_LENGTH_NICKNAME + " to " + NicknameConstants.MAX_LENGTH_NICKNAME + " characters")
    private String nickname;

    @Override
    public String toString(){
        return "UserUpdateRequest: " +
                "\n\tname:      " + name +
                "\n\temail:     " + email +
                "\n\tnickname:  " + nickname;
    }
}
