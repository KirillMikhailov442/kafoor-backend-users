package kafoor.users.user_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kafoor.users.user_service.models.enums.UserRoles;
import kafoor.users.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin", description = "Official admin API")
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @SecurityRequirement(name = "JWT")
    @PatchMapping("/assign-role/{role}")
    public ResponseEntity<String> assignRole(@PathVariable UserRoles role) {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.addRole(role, userId);
        return ResponseEntity.ok("The role is successfully assigned");
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/take-role/{role}")
    public ResponseEntity<String> takeRole(@PathVariable UserRoles role) {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.removeRole(role, userId);
        return ResponseEntity.ok("The role is successfully taken away");
    }

    @SecurityRequirement(name = "JWT")
    @PatchMapping("/ban/{id}")
    public ResponseEntity<String> banUser(@PathVariable long id){
        userService.banUser(id);
        return ResponseEntity.ok("User successfully banned");
    }

    @SecurityRequirement(name = "JWT")
    @PatchMapping("/unban/{id}")
    public ResponseEntity<String> unbanUser(@PathVariable long id){
        userService.unbanUser(id);
        return ResponseEntity.ok("User successfully unbanned");
    }
}
