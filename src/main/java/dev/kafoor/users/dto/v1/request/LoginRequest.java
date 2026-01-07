package dev.kafoor.users.dto.v1.request;

import dev.kafoor.users.constant.EmailConstants;
import dev.kafoor.users.constant.PasswordConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "email must be not empty")
    @Email(message = "email must be valid")
    @Size(
            min = EmailConstants.MIN_LENGTH_EMAIL,
            max = EmailConstants.MAX_LENGTH_EMAIL,
            message = "email must be between " + EmailConstants.MIN_LENGTH_EMAIL + " and " + EmailConstants.MAX_LENGTH_EMAIL + " characters long"
    )
    private String email;

    @NotBlank(message = "password must be not empty")
    @Size(
            min = PasswordConstants.MIN_LENGTH_PASSWORD,
            max = PasswordConstants.MAX_LENGTH_PASSWORD,
            message = "password must be between " + PasswordConstants.MIN_LENGTH_PASSWORD + " and " + PasswordConstants.MAX_LENGTH_PASSWORD + " characters long"
    )
    private String password;

    @Override
    public String toString(){
        return "LoginRequest:" +
                "\n\temail:    " + email +
                "\n\tpassword: " + password;
    }
}
