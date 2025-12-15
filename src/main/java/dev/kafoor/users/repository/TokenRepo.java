package dev.kafoor.users.repository;

import dev.kafoor.users.entity.Token;
import dev.kafoor.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    public List<Token> findAllByUserId(long userId);

    public Optional<Token> findByRefreshToken(String refresh);

    Optional<Token> findByUserAndUserAgentAndIp(User user, String userAgent, String ip);

    Optional<Token> findByUserIdAndUserAgentAndIp(long userId, String userAgent, String ip);
}