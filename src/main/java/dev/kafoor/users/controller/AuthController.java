package dev.kafoor.users.controller;

import dev.kafoor.users.dto.internal.HttpHeader;
import dev.kafoor.users.dto.internal.UserCreated;
import dev.kafoor.users.dto.request.LoginRequest;
import dev.kafoor.users.dto.request.RegisterRequest;
import dev.kafoor.users.dto.response.LoginResponse;
import dev.kafoor.users.mapper.UserMapper;
import dev.kafoor.users.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest dto){
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest dto, HttpServletRequest request){
        HttpHeader header = HttpHeader.builder()
                .ip(request.getRemoteAddr())
                .userAgent(request.getHeader("User-Agent"))
                .build();
        UserCreated newUser = userService.createUser(userMapper.toUserCreate(dto), header);
        return ResponseEntity.ok(userMapper.toRegisterResponse(newUser));

    }
}
