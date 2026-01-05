package dev.kafoor.users.mapper;

import dev.kafoor.users.dto.internal.*;
import dev.kafoor.users.dto.request.LoginRequest;
import dev.kafoor.users.dto.request.RegisterRequest;
import dev.kafoor.users.dto.request.UserUpdateRequest;
import dev.kafoor.users.dto.response.LoginResponse;
import dev.kafoor.users.dto.response.UserResponse;
import dev.kafoor.users.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    default UserCreate toUserCreate(RegisterRequest register){
        return UserCreate.builder()
                .name(register.getName())
                .email(register.getEmail())
                .nickname(register.getNickname())
                .password(register.getPassword())
                .build();
    }

    default UserCreated toUserCreated(UserEntity userEntity, String accessToken, String refreshToken){
        return UserCreated.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .isConfirmed(userEntity.isConfirmed())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    default LoginResponse toRegisterResponse(UserCreated userCreated){
        UserResponse user = UserResponse.builder()
                .name(userCreated.getName())
                .email(userCreated.getEmail())
                .nickname(userCreated.getNickname())
                .isConfirmed(userCreated.isConfirmed())
                .build();

        return LoginResponse.builder()
                .user(user)
                .accessToken(userCreated.getAccessToken())
                .refreshToken(userCreated.getRefreshToken())
                .build();
    }

    default LoginResponse toLoginResponse(UserLogined userLogined){
        UserResponse user = UserResponse.builder()
                .name(userLogined.getUser().getName())
                .email(userLogined.getUser().getEmail())
                .nickname(userLogined.getUser().getNickname())
                .isConfirmed(userLogined.getUser().isConfirmed())
                .build();

        return LoginResponse.builder()
                .user(user)
                .accessToken(userLogined.getAccessToken())
                .refreshToken(userLogined.getRefreshToken())
                .build();
    }

    @Mapping(target = "roles", ignore = true)
    UserResponse toResponse(UserEntity user);

    UserLogin toUserLogin(LoginRequest loginResponse);

    default UserUpdate toUserUpdate(UserUpdateRequest request){
        return UserUpdate.builder()
                .name(request.getName())
                .email(request.getEmail())
                .nickname(request.getNickname())
                .build();
    }
}
