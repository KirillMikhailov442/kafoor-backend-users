package kafoor.users.user_service.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kafoor.users.user_service.dtos.*;
import kafoor.users.user_service.models.User;
import kafoor.users.user_service.services.UserService;
import kafoor.users.user_service.utils.GeneratorKeys;
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
    @Autowired
    private GeneratorKeys generatorKeys;

    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable long id) {
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @SecurityRequirement(name = "JWT")
    @GetMapping("/profile")
    public ResponseEntity<User> profile() {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserCreateResDTO> register(@Valid @RequestBody UserCreateReqDTO dto,
            HttpServletRequest request) {
        HeaderDTO headerDto = HeaderDTO.builder()
                .IP(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent")).build();
        UserCreateResDTO user = userService.createUser(dto, headerDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokensDTO> login(@RequestBody LoginDTO dto, HttpServletRequest request) {
        HeaderDTO headerDto = HeaderDTO.builder()
                .IP(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent")).build();
        TokensDTO tokens = userService.login(dto, headerDto);
        return ResponseEntity.ok(tokens);
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping()
    public ResponseEntity<User> update(@Valid @RequestBody UserUpdateDTO dto) {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        User user = userService.updateUser(dto, userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update-tokens")
    public ResponseEntity<TokensDTO> updateRefreshToken(@Valid @RequestBody TokenCreateDTO dto) {
        return new ResponseEntity<>(userService.updateRefreshTokenOfUser(dto), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "JWT")
    @PatchMapping("/password-change")
    public ResponseEntity<String> passwordChange(@Valid @RequestBody PasswordChangeDTO dto) {
        long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        userService.passwordChangeOfUser(dto, userId);
        return ResponseEntity.ok("The password is successfully changed");
    }

    @PatchMapping("/confirm/{code}")
    public ResponseEntity<String> confirmUser(
            @Valid @PathVariable @NotNull(message = "Code must not be null") String code) {
        try {
            long userId = Long.parseLong(generatorKeys.decode(code));
            String firstName = userService.findUserById(userId).getName();
            userService.confirmUser(userId);
            return ResponseEntity.ok(String.format("User %s has been successfully verified", firstName));
        } catch (Exception ex) {
            return new ResponseEntity<>("Failed to verify user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User successfully deleted");
    }

    @SecurityRequirement(name = "JWT")
    @PostMapping("/by-ids")
    public ResponseEntity<List<User>> findUsersByIds(@RequestBody UsersByIds dto) {
        List<User> users = userService.findAllUsersByIds(dto.getUsersId());
        return ResponseEntity.ok(users);
    }

}
