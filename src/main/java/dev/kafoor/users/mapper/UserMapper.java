package dev.kafoor.users.mapper;

import dev.kafoor.users.dto.v1.internal.*;
import dev.kafoor.users.dto.v1.request.LoginRequest;
import dev.kafoor.users.dto.v1.request.RegisterRequest;
import dev.kafoor.users.dto.v1.request.UserUpdateRequest;
import dev.kafoor.users.dto.v1.response.LoginResponse;
import dev.kafoor.users.dto.v1.response.UserResponse;
import dev.kafoor.users.entity.RoleEntity;
import dev.kafoor.users.entity.UserEntity;
import dev.kafoor.users.entity.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default UserCreate toUserCreate(RegisterRequest register) {
        return UserCreate.builder()
                .name(register.getName())
                .email(register.getEmail())
                .nickname(register.getNickname())
                .password(register.getPassword())
                .role(register.getRole())
                .build();
    }

    default UserCreated toUserCreated(UserEntity userEntity, String accessToken, String refreshToken) {
        Set<UserRole> userRoles = userEntity.getRoles()
                .stream()
                .map(RoleEntity::getName)
                .collect(Collectors.toSet());

        return UserCreated.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .isConfirmed(userEntity.isConfirmed())
                .roles(userRoles)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    default LoginResponse toRegisterResponse(UserCreated userCreated) {
        UserResponse user = UserResponse.builder()
                .id(userCreated.getId())
                .name(userCreated.getName())
                .email(userCreated.getEmail())
                .nickname(userCreated.getNickname())
                .isConfirmed(userCreated.isConfirmed())
                .roles(userCreated.getRoles())
                .build();

        return LoginResponse.builder()
                .user(user)
                .accessToken(userCreated.getAccessToken())
                .refreshToken(userCreated.getRefreshToken())
                .build();
    }

    default LoginResponse toLoginResponse(UserLogined userLogined) {
        UserResponse user = UserResponse.builder()
                .id(userLogined.getUser().getId())
                .name(userLogined.getUser().getName())
                .email(userLogined.getUser().getEmail())
                .nickname(userLogined.getUser().getNickname())
                .isConfirmed(userLogined.getUser().isConfirmed())
                .roles(userLogined.getUser().getRoles())
                .build();

        return LoginResponse.builder()
                .user(user)
                .accessToken(userLogined.getAccessToken())
                .refreshToken(userLogined.getRefreshToken())
                .build();
    }

    @Mapping(source = "confirmed", target = "isConfirmed")
    @Mapping(source = "roles", target = "roles")
    UserResponse toUserResponse(UserEntity userEntity);

    default String mapRoleToString(RoleEntity role) {
        return role == null || role.getName() == null ? null : role.getName().name();
    }

    UserLogin toUserLogin(LoginRequest loginResponse);

    default UserUpdate toUserUpdate(UserUpdateRequest request) {
        return UserUpdate.builder()
                .name(request.getName())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();
    }
}
