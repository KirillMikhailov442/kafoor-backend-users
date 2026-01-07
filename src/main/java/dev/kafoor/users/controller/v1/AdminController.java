package dev.kafoor.users.controller.v1;

import dev.kafoor.users.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "admin")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {
    private final UserService userService;

    public ResponseEntity<String> banUser(@PathVariable long id){
        userService.banUser(id);
        return ResponseEntity.ok("user successfully banned");
    }

    public ResponseEntity<String> unban(@PathVariable long id){
        userService.unbanUser(id);
        return ResponseEntity.ok("user successfully unbanned");
    }
}
