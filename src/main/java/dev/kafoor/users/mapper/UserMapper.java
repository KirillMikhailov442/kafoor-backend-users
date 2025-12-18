package dev.kafoor.users.mapper;

import dev.kafoor.users.dto.internal.UserCreate;
import dev.kafoor.users.dto.internal.UserCreated;
import dev.kafoor.users.dto.request.RegisterRequest;
import dev.kafoor.users.dto.response.LoginResponse;
import dev.kafoor.users.dto.response.UserResponse;
import dev.kafoor.users.entity.User;
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

    default UserCreated toUserCreated(User user, String accessToken, String refreshToken){
        return UserCreated.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .isConfirmed(user.isConfirmed())
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

    User toEntity(UserResponse userResponse);
}
