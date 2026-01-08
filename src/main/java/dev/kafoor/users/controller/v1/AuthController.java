package dev.kafoor.users.controller.v1;

import dev.kafoor.users.dto.v1.internal.HttpHeader;
import dev.kafoor.users.dto.v1.internal.UserCreated;
import dev.kafoor.users.dto.v1.internal.UserLogined;
import dev.kafoor.users.dto.v1.request.LoginRequest;
import dev.kafoor.users.dto.v1.request.RegisterRequest;
import dev.kafoor.users.dto.v1.request.TokenCreateRequest;
import dev.kafoor.users.dto.v1.response.ErrorResponse;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "Authentication", description = "Endpoints for user registration, login, and token refresh")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    public final JWTService jwtService;

    @Operation(
            summary = "Authenticate user",
            description = "Authenticates a user with email and password, and returns access and refresh tokens."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully authenticated",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid credentials or request format",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication failed (e.g., wrong password or unverified email)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest http
    ) {
        HttpHeader header = HttpHeaderExtractor.extract(http);
        UserLogined userLogined = userService.login(userMapper.toUserLogin(request), header);
        return ResponseEntity.ok(userMapper.toLoginResponse(userLogined));
    }

    @Operation(
            summary = "Register a new user",
            description = "Registers a new user account and returns authentication tokens upon successful registration."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User successfully registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data (e.g., email already taken, weak password)",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "User with this email already exists",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletRequest http
    ) {
        HttpHeader header = HttpHeaderExtractor.extract(http);
        UserCreated newUser = userService.createUser(userMapper.toUserCreate(request), header);
        return new ResponseEntity<>(userMapper.toRegisterResponse(newUser), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Refresh access and refresh tokens",
            description = "Issues new access and refresh tokens using a valid (non-expired) refresh token."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "New tokens successfully issued",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokensResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Refresh token is missing, invalid, or expired",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
                    )
    })
    @PatchMapping("/update-tokens")
    public ResponseEntity<TokensResponse> updateTokens(@Valid @RequestBody TokenCreateRequest request) {
        String currentRefresh = request.getRefreshToken();
        if (jwtService.isTokenExpired(currentRefresh, Token.REFRESH)) {
            throw new BadRequest("Token expired");
        }

        TokenEntity tokenEntity = tokenService.findTokenByRefreshOrThrow(currentRefresh);
        UserPrincipal userPrincipal = new UserPrincipal(tokenEntity.getUser());

        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("roles", userPrincipal.getAuthorities());

        String accessToken = jwtService.generateToken(userPrincipal, userClaims, Token.ACCESS);
        String refreshToken = jwtService.generateToken(userPrincipal, userClaims, Token.REFRESH);

        TokensResponse tokensResponse = TokensResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return ResponseEntity.ok(tokensResponse);
    }
}