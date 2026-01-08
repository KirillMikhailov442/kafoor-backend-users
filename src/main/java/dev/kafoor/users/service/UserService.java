package dev.kafoor.users.service;

import dev.kafoor.users.dto.v1.internal.*;
import dev.kafoor.users.dto.v1.request.enums.RegisterRole;
import dev.kafoor.users.entity.RoleEntity;
import dev.kafoor.users.entity.TokenEntity;
import dev.kafoor.users.entity.UserEntity;
import dev.kafoor.users.entity.enums.Token;
import dev.kafoor.users.entity.enums.UserRole;
import dev.kafoor.users.exception.BadRequest;
import dev.kafoor.users.exception.Conflict;
import dev.kafoor.users.exception.NotFound;
import dev.kafoor.users.mapper.UserMapper;
import dev.kafoor.users.repository.UserRepo;
import dev.kafoor.users.security.JWTService;
import dev.kafoor.users.security.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final JWTService jwtService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<UserEntity> findAllUsers() {
        List<UserEntity> userEntities = userRepo.findAll();
        if (userEntities.isEmpty())
            throw new NotFound("users not found");
        return userEntities;
    }

    public Optional<UserEntity> findUserById(long id) {
        return userRepo.findById(id);
    }

    public UserEntity findUserByIdOrThrow(long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFound("user not found by id"));
    }

    public Optional<UserEntity> findUserByNickname(String nickname) {
        return userRepo.findByNickname(nickname);
    }

    public UserEntity findUserByNicknameOrThrow(String nickname) {
        return userRepo.findByNickname(nickname).orElseThrow(() -> new NotFound(("user not found by nickname")));
    }

    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public UserEntity findUserByEmailOrThrow(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new NotFound("user not found by email"));
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return userRepo.existsByNickname(nickname);
    }

    @Transactional
    public UserCreated createUser(UserCreate userDto, HttpHeader header) {
        if (existsByEmail(userDto.getEmail()))
            throw new Conflict(("a user with such email already exists"));
        if (existsByNickname(userDto.getNickname()))
            throw new Conflict(("a user with such nickname already exists"));

        UserRole newUserRole = userDto.getRole().equals(RegisterRole.STUDENT)
                ? UserRole.STUDENT
                : UserRole.TEACHER;

        Set<RoleEntity> roles = Set.of(
                roleService.findOrCreateRole(UserRole.USER),
                roleService.findOrCreateRole(newUserRole));

        UserEntity newUserEntity = UserEntity.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .isConfirmed(false)
                .passwordHash(encoder.encode(userDto.getPassword()))
                .roles(roles).build();
        UserEntity createdUserEntity = userRepo.save(newUserEntity);

        UserPrincipal userPrincipal = loadUserByUsername(String.valueOf(newUserEntity.getId()));
        String accessToken = jwtService.generateToken(userPrincipal, Token.ACCESS);
        String refreshToken = jwtService.generateToken(userPrincipal, Token.REFRESH);

        TokenCreate tokenCreateDto = TokenCreate.builder()
                .ip(header.getIp())
                .userAgent(header.getUserAgent())
                .refresh(refreshToken)
                .build();
        tokenService.createToken(tokenCreateDto, createdUserEntity);

        return UserCreated.builder()
                .id(createdUserEntity.getId())
                .name(createdUserEntity.getName())
                .email(createdUserEntity.getEmail())
                .nickname(createdUserEntity.getNickname())
                .isConfirmed(createdUserEntity.isConfirmed())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public UserEntity updateUser(UserUpdate dto, long id) {
        UserEntity userEntity = findUserByIdOrThrow(id);
        if (userRepo.existsByEmail(dto.getEmail()) && userEntity.getId() != id)
            throw new Conflict("a user with such email already exists");
        if (userRepo.existsByNickname(dto.getNickname()) && userEntity.getId() != id)
            throw new Conflict("a user with such nickname already exists");

        if (dto.getName() != null && !dto.getName().isEmpty())
            userEntity.setName(dto.getName());
        if (dto.getEmail() != null && !dto.getEmail().isEmpty())
            userEntity.setEmail(dto.getEmail());
        if (dto.getNickname() != null && !dto.getNickname().isEmpty())
            userEntity.setNickname(dto.getNickname());

        return userRepo.save(userEntity);
    }

    @Transactional
    public UserLogined login(UserLogin dto, HttpHeader header) {
        UserEntity user = findUserByEmailOrThrow(dto.getEmail());
        if (!encoder.matches(dto.getPassword(), user.getPasswordHash()))
            throw new BadRequest("incorrect password");
        UserPrincipal userPrincipal = new UserPrincipal(user);
        TokenEntity token = null;

        try {
            token = tokenService.findTokenByUserAndUserAgentAndIpOrThrow(user, header.getUserAgent(), header.getIp());
        } catch (NotFound notFound) {
            String refreshToken = jwtService.generateToken(userPrincipal, Token.REFRESH);
            TokenCreate tokenCreate = TokenCreate.builder()
                    .ip(header.getIp())
                    .userAgent(header.getUserAgent())
                    .refresh(refreshToken)
                    .build();
            token = tokenService.createToken(tokenCreate, user);
        }

        Map<String, Object> userClaims = new HashMap<>();
        userClaims.put("roles", userPrincipal.getAuthorities());

        String accessToken = jwtService.generateToken(userPrincipal, userClaims, Token.ACCESS);
        String refreshToken = jwtService.generateToken(userPrincipal, userClaims, Token.REFRESH);
        tokenService.updateRefreshToken(refreshToken, token.getId());

        return UserLogined.builder()
                .user(userMapper.toUserResponse(user))
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void passwordChange(PasswordChange dto, long id) {
        UserEntity userEntity = findUserByIdOrThrow(id);
        if (!encoder.matches(dto.getCurrentPassword(), userEntity.getPasswordHash()))
            throw new Conflict("current password does not match user password");
        userEntity.setPasswordHash(encoder.encode(dto.getNewPassword()));
    }

    public void banUser(long id) {
        UserEntity userEntity = findUserByIdOrThrow(id);
        userEntity.setDeactivatedAt(LocalDateTime.now());
        userRepo.save(userEntity);
    }

    public void unbanUser(long id) {
        UserEntity userEntity = findUserByIdOrThrow(id);
        userEntity.setDeactivatedAt(null);
        userRepo.save(userEntity);
    }

    public void confirmUser(long id) {
        UserEntity userEntity = findUserByIdOrThrow(id);
        userEntity.setConfirmed(true);
        userRepo.save(userEntity);
    }

    public void deleteUserById(long id) {
        userRepo.deleteById(id);
    }

    @Transactional
    public void addRoleToUser(UserRole newRole, long id) {
        UserEntity userEntity = findUserByIdOrThrow(id);
        boolean hasRole = userEntity.getRoles().stream()
                .anyMatch(role -> role.getName().equals(newRole));
        if (hasRole)
            throw new Conflict(
                    String.format("the role of %s has already been issued to this user", newRole.toString()));
        Set<RoleEntity> roles = userEntity.getRoles();
        RoleEntity newRoleEntity = roleService.findOrCreateRole(newRole);
        roles.add(newRoleEntity);
        userEntity.setRoles(roles);
        userRepo.save(userEntity);
    }

    @Transactional
    public void removeRoleFromUser(UserRole currentRole, long id) {
        UserEntity userEntity = findUserByIdOrThrow(id);
        boolean hasRole = userEntity.getRoles().stream()
                .anyMatch(role -> role.getName().equals(currentRole));
        if (!hasRole)
            throw new Conflict(String.format("the role of the %s is not of this role", currentRole.toString()));
        RoleEntity removeRole = roleService.findRoleByNameOrThrow(currentRole);
        userEntity.getRoles().remove(removeRole);
        userRepo.save(userEntity);
    }

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findById(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserPrincipal(userEntity);
    }
}
