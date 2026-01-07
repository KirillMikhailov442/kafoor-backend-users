package dev.kafoor.users.controller.v1;

import dev.kafoor.users.dto.v1.internal.HttpHeader;
import dev.kafoor.users.dto.v1.internal.UserCreated;
import dev.kafoor.users.dto.v1.internal.UserLogined;
import dev.kafoor.users.dto.v1.request.LoginRequest;
import dev.kafoor.users.dto.v1.request.RegisterRequest;
import dev.kafoor.users.dto.v1.request.TokenCreateRequest;
import dev.kafoor.users.dto.v1.response.LoginResponse;
import dev.kafoor.users.dto.v1.response.TokensResponse;
import dev.kafoor.users.entity.TokenEntity;
import dev.kafoor.users.entity.enums.Token;
import dev.kafoor.users.exception.BadRequest;
import dev.kafoor.users.mapper.UserMapper;
import dev.kafoor.users.security.JWTService;
import dev.kafoor.users.security.UserPrincipal;
import dev.kafoor.users.service.TokenService;
import dev.kafoor.users.service.UserService;
import dev.kafoor.users.util.HttpHeaderExtractor;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    public final JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request, HttpServletRequest http){
        HttpHeader header = HttpHeaderExtractor.extract(http);
        UserLogined userLogined = userService.login(userMapper.toUserLogin(request), header);
        return ResponseEntity.ok(userMapper.toLoginResponse(userLogined));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@Valid @RequestBody RegisterRequest request, HttpServletRequest http){
        HttpHeader header = HttpHeaderExtractor.extract(http);
        UserCreated newUser = userService.createUser(userMapper.toUserCreate(request), header);
        return new ResponseEntity<>(userMapper.toRegisterResponse(newUser), HttpStatus.CREATED);
    }

    @PatchMapping("/update-tokens")
    public ResponseEntity<TokensResponse> updateTokens(@Valid @RequestBody TokenCreateRequest request){
        String currentRefresh = request.getRefreshToken();
        if (!jwtService.isTokenExpired(currentRefresh, Token.REFRESH)) {
            throw new BadRequest("Token expired");
        }

        TokenEntity tokenEntity = tokenService.findTokenByRefreshOrThrow(currentRefresh);
        UserPrincipal userPrincipal = new UserPrincipal(tokenEntity.getUser());

        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("roles", userPrincipal.getAuthorities());

        String accessToken = jwtService.generateToken(userPrincipal, userClaims, Token.ACCESS);
        String refreshToken = jwtService.generateToken(userPrincipal, userClaims, Token.REFRESH);

        TokensResponse tokensResponse = TokensResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
        return ResponseEntity.ok(tokensResponse);
    }
}
