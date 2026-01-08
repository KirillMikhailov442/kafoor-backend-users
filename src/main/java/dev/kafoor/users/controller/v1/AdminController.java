package dev.kafoor.users.controller.v1;

import dev.kafoor.users.dto.v1.request.AddRoleToUserRequest;
import dev.kafoor.users.dto.v1.response.ErrorResponse;
import dev.kafoor.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin Management", description = "Administrative endpoints for user role assignment and ban/unban operations")
@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/admin")
public class AdminController {
    private final UserService userService;

    @Operation(
            summary = "Assign a role to a user",
            description = "Grants a specified role to a user. Requires admin privileges."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Role successfully assigned",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID or request format",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied: admin privileges required",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User or role not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/users/{userId}/roles")
    public ResponseEntity<String> addRoleToUser(
            @Parameter(description = "ID of the user to assign the role to", required = true, example = "123")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long userId,
            @Valid @RequestBody AddRoleToUserRequest request
    ) {
        userService.addRoleToUser(request.getRole(), userId);
        return ResponseEntity.ok("role was successfully added to the user");
    }

    @Operation(
            summary = "Ban a user",
            description = "Permanently or temporarily bans a user from the system. Requires admin privileges."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully banned",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied: admin privileges required",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/ban/{id}")
    public ResponseEntity<String> banUser(
            @Parameter(description = "ID of the user to ban", required = true, example = "123")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id
    ) {
        userService.banUser(id);
        return ResponseEntity.ok("user successfully banned");
    }

    @Operation(
            summary = "Unban a user",
            description = "Removes ban status from a previously banned user. Requires admin privileges."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully unbanned",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid user ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied: admin privileges required",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/unban/{id}")
    public ResponseEntity<String> unban(
            @Parameter(description = "ID of the user to unban", required = true, example = "123")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id
    ) {
        userService.unbanUser(id);
        return ResponseEntity.ok("user successfully unbanned");
    }
}