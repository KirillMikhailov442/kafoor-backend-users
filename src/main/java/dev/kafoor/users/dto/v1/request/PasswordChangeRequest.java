package dev.kafoor.users.dto.v1.request;

import dev.kafoor.users.constant.PasswordConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {
    @NotBlank(message = "Current password is mandatory")
    @Size(
            min = PasswordConstants.MIN_LENGTH_PASSWORD,
            max = PasswordConstants.MAX_LENGTH_PASSWORD,
            message = "Password length should be " + PasswordConstants.MIN_LENGTH_PASSWORD + " to " + PasswordConstants.MAX_LENGTH_PASSWORD + " characters"
    )
    private String currentPassword;

    @NotBlank(message = "New password is mandatory")
    @Size(
            min = PasswordConstants.MIN_LENGTH_PASSWORD,
            max = PasswordConstants.MAX_LENGTH_PASSWORD,
            message = "Password length should be " + PasswordConstants.MIN_LENGTH_PASSWORD + " to " + PasswordConstants.MAX_LENGTH_PASSWORD + " characters"
    )
    private String newPassword;

    @Override
    public String toString(){
        return "PasswordChangeRequest:" +
                "\n\tcurrentPassword:  " + currentPassword +
                "\n\tnewPassword:      " + newPassword;
    }
}
