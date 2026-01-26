package dev.kafoor.users.dto.v1.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UsersByIdsRequest {
    @NotNull(message = "user IDs list cannot be null")
    @NotEmpty(message = "user IDs list cannot be empty")
    private List<@Positive(message = "each user ID must be positive") Long> ids;
}
