package dev.kafoor.users.dto.v1.request;

import dev.kafoor.users.constant.EmailConstants;
import dev.kafoor.users.constant.NicknameConstants;
import dev.kafoor.users.constant.PasswordConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

enum RegisterRole {
    TEACHER,
    STUDENT
}

@Getter
public class RegisterRequest {
    @NotBlank(message = "name is mandatory")
    @Size(
            min = 2,
            max = 32,
            message = "name must be between 2 and 32 characters long")
    private String name;

    @NotBlank(message = "email must be not empty")
    @Email(message = "email must be valid")
    @Size(
            min = EmailConstants.MIN_LENGTH_EMAIL,
            max = EmailConstants.MAX_LENGTH_EMAIL,
            message = "email must be between " + EmailConstants.MIN_LENGTH_EMAIL + " and " + EmailConstants.MAX_LENGTH_EMAIL + " characters long"
    )
    private String email;

    @NotBlank(message = "nickname must be not empty")
    @Size(
            min = NicknameConstants.MIN_LENGTH_NICKNAME,
            max = NicknameConstants.MAX_LENGTH_NICKNAME,
            message = "nickname must be between " + NicknameConstants.MIN_LENGTH_NICKNAME + " and " + NicknameConstants.MAX_LENGTH_NICKNAME + " characters long"
    )
    private String nickname;

    @NotBlank(message = "password must be not empty")
    @Size(
            min = PasswordConstants.MIN_LENGTH_PASSWORD,
            max = PasswordConstants.MAX_LENGTH_PASSWORD,
            message = "password must be between " + PasswordConstants.MIN_LENGTH_PASSWORD + " and " + PasswordConstants.MAX_LENGTH_PASSWORD + " characters long"
    )
    private String password;

    private RegisterRole role;

    @Override
    public String toString(){
        return "RegisterRequest:" +
                "\n\tname:     " + name +
                "\n\temail:    " + email +
                "\n\tnickname: " + nickname +
                "\n\tpassword: " + password;
    }
}
