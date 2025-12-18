package dev.kafoor.users.service;

import dev.kafoor.users.dto.internal.HttpHeader;
import dev.kafoor.users.dto.internal.UserCreate;
import dev.kafoor.users.dto.internal.UserCreated;
import dev.kafoor.users.dto.request.RegisterRequest;
import dev.kafoor.users.entity.Role;
import dev.kafoor.users.entity.User;
import dev.kafoor.users.entity.enums.Token;
import dev.kafoor.users.exception.Conflict;
import dev.kafoor.users.exception.NotFound;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final JWTService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public List<User> findAllUsers() {
        List<User> users = userRepo.findAll();
        if (users.isEmpty())
            throw new NotFound("Users not found");
        return users;
    }

    public Optional<User> findUserById(long id){
        return userRepo.findById(id);
    }

    public User findUserByIdOrThrow(long id) {
        return userRepo.findById(id).orElseThrow(() -> new NotFound("User not found by id"));
    }

    public Optional<User> findUserByNickname(String nickname){
        return userRepo.findByNickname(nickname);
    }

    public User findUserByNicknameOrThrow(String nickname) {
        return userRepo.findByNickname(nickname).orElseThrow(() -> new NotFound(("User not found by nickname")));
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User findUserByEmailOrThrow(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new NotFound("User not found by email"));
    }

    public boolean existsByEmail(String email){
        return userRepo.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname){
        return userRepo.existsByNickname(nickname);
    }

    @Transactional
    public UserCreated createUser(UserCreate userDto, HttpHeader header){
        if (existsByEmail(userDto.getEmail())) throw new Conflict(("A user with such email already exists"));
        if (existsByNickname(userDto.getNickname())) throw new Conflict(("A user with such nickname already exists"));

        Set<Role> roles = null;

        User newUser = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .nickname(userDto.getNickname())
                .isConfirmed(false)
                .passwordHash(encoder.encode(userDto.getPassword()))
                .roles(roles).build();
        User createdUser = userRepo.save(newUser);

        UserPrincipal userPrincipal = loadUserByUsername(String.valueOf(newUser.getId()));
        String accessToken = jwtService.generateToken(userPrincipal, Token.ACCESS);
        String refreshToken = jwtService.generateToken(userPrincipal, Token.REFRESH);

        return UserCreated.builder()
                .id(createdUser.getId())
                .name(createdUser.getName())
                .email(createdUser.getEmail())
                .nickname(createdUser.getNickname())
                .isConfirmed(createdUser.isConfirmed())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public User updateUser(){

    }

    public void banUser(long id) {
        User user = findUserByIdOrThrow(id);
        user.setDeactivatedAt(LocalDateTime.now());
        userRepo.save(user);
    }

    public void unbanUser(long id) {
        User user = findUserByIdOrThrow(id);
        user.setDeactivatedAt(null);
        userRepo.save(user);
    }

    public void confirmUser(long id) {
        User user = findUserByIdOrThrow(id);
        user.setConfirmed(true);
        userRepo.save(user);
    }

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new UserPrincipal(user);
    }
}
