package kafoor.users.user_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kafoor.users.user_service.constants.NicknameConstants;
import kafoor.users.user_service.constants.Regexps;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdateDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 32, message = "Name length should be 2 to 32 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Incorrect email")
    private String email;

    @NotBlank(message = "Nickname is mandatory")
    @Pattern(regexp = Regexps.regexNickname, message = "The nickname should start with @")
    @Size(min = NicknameConstants.MIN_LENGTH_NICKNAME, max = NicknameConstants.MAX_LENGTH_NICKNAME, message = "Password length should be " + NicknameConstants.MIN_LENGTH_NICKNAME + " to " + NicknameConstants.MAX_LENGTH_NICKNAME + " characters")
    private String nickname;

    @Override
    public String toString() {
        return String.format("name: %s \n email: %s \n nickname: %s", name, email, nickname);

    }
}
