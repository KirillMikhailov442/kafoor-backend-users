package dev.kafoor.users.dto.v1.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenCreateRequest {
    @NotBlank(message = "refresh token is mandatory")
    private String refreshToken;

    @Override
    public String toString() {
        return "TokenCreateRequest: " +
                "\n\trefreshToken:  " + refreshToken;
    }
}
