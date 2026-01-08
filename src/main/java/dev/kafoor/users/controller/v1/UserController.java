package dev.kafoor.users.controller.v1;

import dev.kafoor.users.dto.v1.internal.PasswordChange;
import dev.kafoor.users.dto.v1.request.PasswordChangeRequest;
import dev.kafoor.users.dto.v1.request.UserUpdateRequest;
import dev.kafoor.users.dto.v1.response.ErrorResponse;
import dev.kafoor.users.dto.v1.response.UserResponse;
import dev.kafoor.users.entity.UserEntity;
import dev.kafoor.users.exception.BadRequest;
import dev.kafoor.users.mapper.UserMapper;
import dev.kafoor.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Tag(name = "User Management", description = "Operations related to users: retrieve, update, delete, and change password")
@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get user by ID", description = "Returns user details by their unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully retrieved",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid ID (must be a positive number)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User with the specified ID not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(
            @Parameter(description = "Unique user identifier", required = true, example = "123")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id) {
        UserEntity userEntity = userService.findUserByIdOrThrow(id);
        return ResponseEntity.ok(userMapper.toUserResponse(userEntity));
    }

    @Operation(summary = "Get all users", description = "Returns a list of all registered users.")
    @ApiResponse(responseCode = "200", description = "List of users successfully retrieved",
            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<UserResponse> users = userService.findAllUsers().stream().map(userMapper::toUserResponse).toList();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Get current user's profile", description = "Returns details of the currently authenticated user.")
    @ApiResponse(
            responseCode = "200",
            description = "Profile successfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> profile() {
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        UserEntity user = userService.findUserByIdOrThrow(userId);
        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    @Operation(summary = "Update current user's profile", description = "Updates profile information for the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile successfully updated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserUpdateRequest request) {
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        UserEntity userEntity = userService.updateUser(userMapper.toUserUpdate(request), userId);
        return ResponseEntity.ok(userMapper.toUserResponse(userEntity));
    }

    @Operation(summary = "Delete user", description = "Deletes a user by ID. Only allowed for the user's own account.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully deleted",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Attempt to delete another user's account or invalid ID",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @Parameter(description = "Unique user identifier", required = true, example = "123")
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id) {
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        if (userId != id) {
            throw new BadRequest("you can't delete someone else's account");
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok("user successfully deleted");
    }

    @Operation(summary = "Change password", description = "Allows the current user to change their password.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Password successfully changed",
                    content = @Content(mediaType = "text/plain", schema = @Schema(implementation = String.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Incorrect current password or invalid new password format",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/password-change")
    public ResponseEntity<String> passwordChange(@Valid @RequestBody PasswordChangeRequest request) {
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        PasswordChange passwordChange = PasswordChange.builder()
                .currentPassword(request.getCurrentPassword())
                .newPassword(request.getNewPassword())
                .build();
        userService.passwordChange(passwordChange, userId);
        return ResponseEntity.ok("the password is successfully changed");
    }
}