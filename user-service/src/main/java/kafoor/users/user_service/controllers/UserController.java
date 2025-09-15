package kafoor.users.user_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kafoor.users.user_service.dtos.*;
import kafoor.users.user_service.models.User;
import kafoor.users.user_service.models.enums.UserRoles;
import kafoor.users.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "Official user API")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable long id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/profile")
    public ResponseEntity<User> profile(){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserCreateResDTO> register(@Valid @RequestBody UserCreateReqDTO dto, HttpServletRequest request){
        HeaderDTO headerDto = HeaderDTO.builder()
                .IP(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent")).build();
        UserCreateResDTO user = userService.createUser(dto, headerDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto){
        TokensDTO tokens = userService.login(dto);
        return ResponseEntity.ok(tokens);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping()
    public ResponseEntity<User> update(@Valid @RequestBody UserUpdateDTO dto){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userService.updateUser(dto, userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update-tokens")
    public ResponseEntity<TokensDTO> updateRefreshToken(@Valid @RequestBody  TokenCreateDTO dto){
        return new ResponseEntity<>(userService.updateRefreshTokenOfUser(dto), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping("/assign-role")
    public ResponseEntity<String> assignRole(@Valid @RequestBody RoleDTO dto){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.addRole(dto.getRole(), userId);
        return ResponseEntity.ok("The role is successfully assigned");
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/take-role/{role}")
    public ResponseEntity<String> takeRole(@PathVariable UserRoles role){
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.removeRole(role, userId);
        return ResponseEntity.ok("The role is successfully taken away");
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User successfully deleted");
    }
}
