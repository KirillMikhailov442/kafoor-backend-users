package dev.kafoor.users.dto.internal;

import dev.kafoor.users.dto.request.PasswordChangeRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordChange {
    private String currentPassword;
    private String newPassword;

    @Override
    public String toString(){
        return "PasswordChangeRequest:" +
                "\n\tcurrentPassword:  " + currentPassword +
                "\n\tnewPassword:      " + newPassword;
    }
}
