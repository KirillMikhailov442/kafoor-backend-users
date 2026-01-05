package dev.kafoor.users.repository;

import dev.kafoor.users.entity.TokenEntity;
import dev.kafoor.users.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<TokenEntity, Long> {
    public List<TokenEntity> findAllByUserId(long userId);

    public Optional<TokenEntity> findByRefresh(String refresh);

    Optional<TokenEntity> findByUserAndUserAgentAndIp(UserEntity userEntity, String userAgent, String ip);

    Optional<TokenEntity> findByUserIdAndUserAgentAndIp(long userId, String userAgent, String ip);
}