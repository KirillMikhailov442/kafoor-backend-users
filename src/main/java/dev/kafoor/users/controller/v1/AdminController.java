package dev.kafoor.users.controller.v1;

import dev.kafoor.users.dto.v1.request.AddRoleToUserRequest;
import dev.kafoor.users.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "admin")
@SecurityRequirement(name = "JWT")
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/admin")
public class AdminController {
    private final UserService userService;

    @PostMapping("/users/{userId}/roles")
    public ResponseEntity<String> addRoleToUser(
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long userId,
            @Valid @RequestBody AddRoleToUserRequest request){
        userService.addRoleToUser(request.getRole(), userId);
        return ResponseEntity.ok("role was successfully added to the user");
    }

    @PatchMapping("/ban/{id}")
    public ResponseEntity<String> banUser(
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id){
        userService.banUser(id);
        return ResponseEntity.ok("user successfully banned");
    }

    @PatchMapping("/unban/{id}")
    public ResponseEntity<String> unban(
            @PathVariable
            @Positive(message = "id must be a positive number")
            Long id){
        userService.unbanUser(id);
        return ResponseEntity.ok("user successfully unbanned");
    }
}
