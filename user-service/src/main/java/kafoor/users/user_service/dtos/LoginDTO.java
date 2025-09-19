package kafoor.users.user_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kafoor.users.user_service.constants.EmailConstants;
import kafoor.users.user_service.constants.PasswordConstants;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Incorrect email")
    @Size(min = EmailConstants.MIN_LENGTH_EMAIL, max = EmailConstants.MAX_LENGTH_EMAIL, message = "Email length should be" + EmailConstants.MIN_LENGTH_EMAIL + "to" + EmailConstants.MAX_LENGTH_EMAIL + "characters")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = PasswordConstants.MIN_LENGTH_PASSWORD, max = PasswordConstants.MAX_LENGTH_PASSWORD, message = "Password length should be " + PasswordConstants.MIN_LENGTH_PASSWORD + " to " + PasswordConstants.MAX_LENGTH_PASSWORD + " characters")
    private String password;
}
