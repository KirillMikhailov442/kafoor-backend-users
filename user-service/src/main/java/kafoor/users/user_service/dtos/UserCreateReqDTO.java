package kafoor.users.user_service.dtos;

import jakarta.validation.constraints.*;
import kafoor.users.user_service.constants.EmailConstants;
import kafoor.users.user_service.constants.NicknameConstants;
import kafoor.users.user_service.constants.PasswordConstants;
import kafoor.users.user_service.constants.Regexps;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCreateReqDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 32, message = "Name length should be 2 to 32 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Incorrect email")
    @Size(min = EmailConstants.MIN_LENGTH_EMAIL, max = EmailConstants.MAX_LENGTH_EMAIL, message = "Email length should be" + EmailConstants.MIN_LENGTH_EMAIL + "to" + EmailConstants.MAX_LENGTH_EMAIL + "characters")
    private String email;

    @NotBlank(message = "Nickname is mandatory")
    @Pattern(regexp = Regexps.regexNickname, message = "The nickname should start with @")
    @Size(min = NicknameConstants.MIN_LENGTH_NICKNAME, max = NicknameConstants.MAX_LENGTH_NICKNAME, message = "Password length should be " + NicknameConstants.MIN_LENGTH_NICKNAME + " to " + NicknameConstants.MAX_LENGTH_NICKNAME + " characters")
    private String nickname;

    @NotBlank(message = "Password is mandatory")
    @Size(min = PasswordConstants.MIN_LENGTH_PASSWORD, max = PasswordConstants.MAX_LENGTH_PASSWORD, message = "Password length should be " + PasswordConstants.MIN_LENGTH_PASSWORD + " to " + PasswordConstants.MAX_LENGTH_PASSWORD + " characters")
    private String password;

    @Override
    public String toString() {
        return String.format("name: %s \n email: %s \n nickname: %s \n password: %s", name, email, nickname, password);
    }
}
