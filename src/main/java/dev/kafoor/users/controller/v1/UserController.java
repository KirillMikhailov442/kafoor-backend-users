package dev.kafoor.users.controller.v1;

import dev.kafoor.users.dto.v1.internal.PasswordChange;
import dev.kafoor.users.dto.v1.request.PasswordChangeRequest;
import dev.kafoor.users.dto.v1.request.UserUpdateRequest;
import dev.kafoor.users.dto.v1.response.UserResponse;
import dev.kafoor.users.entity.UserEntity;
import dev.kafoor.users.exception.BadRequest;
import dev.kafoor.users.mapper.UserMapper;
import dev.kafoor.users.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Tag(name = "user")
@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/v1/user")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable long id){
        UserEntity userEntity = userService.findUserByIdOrThrow(id);
        return ResponseEntity.ok(userMapper.toResponse(userEntity));
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll(){
        List<UserResponse> users = userService.findAllUsers().stream().map(userMapper::toResponse).toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> profile(){
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        UserEntity user = userService.findUserByIdOrThrow(userId);
        return ResponseEntity.ok(userMapper.toResponse(user));
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserUpdateRequest request){
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        UserEntity userEntity = userService.updateUser(userMapper.toUserUpdate(request), userId);

        return ResponseEntity.ok(userMapper.toResponse(userEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        if(userId != id){
            throw new BadRequest("you can't delete someone else's account");
        }
        userService.deleteUserById(id);
        return ResponseEntity.ok("user successfully deleted");
    }

    @PatchMapping("/password-change")
    public ResponseEntity<String> passwordChange(@Valid @RequestBody PasswordChangeRequest request){
        long userId = Long.parseLong(Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName());
        PasswordChange passwordChange = PasswordChange.builder()
                .currentPassword(request.getCurrentPassword())
                .newPassword(request.getNewPassword())
                .build();
        userService.passwordChange(passwordChange, userId);
        return ResponseEntity.ok("the password is successfully changed");
    }
}
