package kafoor.users.user_service.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kafoor.users.user_service.dtos.*;
import kafoor.users.user_service.models.User;
import kafoor.users.user_service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "Official user API")
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable long id){
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
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
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@Valid @RequestBody UserUpdateDTO dto, @PathVariable long id){
        User user = userService.updateUser(dto, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping("/update-tokens")
    public ResponseEntity<TokensDTO> updateRefreshToken(@Valid @RequestBody  TokenCreateDTO dto){
        return new ResponseEntity<>(userService.updateRefreshTokenOfUser(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        userService.deleteUser(id);
        return new ResponseEntity<>("User successfully deleted", HttpStatus.OK);
    }
}
