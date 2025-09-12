package kafoor.users.user_service.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kafoor.users.user_service.constants.PasswordConstants;

public class PasswordChangeDTO {
    @NotBlank(message = "Current password is mandatory")
    @Size(min = PasswordConstants.MIN_LENGTH_PASSWORD, max = PasswordConstants.MAX_LENGTH_PASSWORD, message = "Password length should be " + PasswordConstants.MIN_LENGTH_PASSWORD + " to " + PasswordConstants.MAX_LENGTH_PASSWORD + " characters")
    private String currentPassword;

    @NotBlank(message = "New password is mandatory")
    @Size(min = PasswordConstants.MIN_LENGTH_PASSWORD, max = PasswordConstants.MAX_LENGTH_PASSWORD, message = "Password length should be " + PasswordConstants.MIN_LENGTH_PASSWORD + " to " + PasswordConstants.MAX_LENGTH_PASSWORD + " characters")
    private String newPassword;
}
