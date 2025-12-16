package dev.kafoor.users.service;

import dev.kafoor.users.entity.Token;
import dev.kafoor.users.entity.User;
import dev.kafoor.users.exception.NotFound;
import dev.kafoor.users.repository.TokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenRepo tokenRepo;

    public List<Token> findAllTokens(){
        return tokenRepo.findAll();
    }

    public List<Token> findTokensByUserId(long userId) {
        return tokenRepo.findAllByUserId(userId);
    }

    public Token findTokenById(long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new NotFound("Token not found with such id"));
    }

    public Optional<Token> findTokenByIdOptional(long id) {
        return tokenRepo.findById(id);
    }

    public Token findTokenByUserIdAndUserAgentAndIP(long userId, String userAgent, String ip) {
        return tokenRepo.findByUserIdAndUserAgentAndIp(userId, userAgent, ip)
                .orElseThrow(() -> new NotFound("Token not found"));
    }

    public Optional<Token> findTokenByUserIdAndUserAgentAndIPOptional(long userId, String userAgent, String ip) {
        return tokenRepo.findByUserIdAndUserAgentAndIp(userId, userAgent, ip);
    }

    public Token findTokenByUserAndUserAgentAndIP(User user, String userAgent, String ip) {
        return tokenRepo.findByUserAndUserAgentAndIp(user, userAgent, ip)
                .orElseThrow(() -> new NotFound("Token not found"));
    }

    public Optional<Token> findTokenByUserAndUserAgentAndIPOptional(User user, String userAgent, String ip) {
        return tokenRepo.findByUserAndUserAgentAndIp(user, userAgent, ip);
    }

    public Token updateRefreshToken(String refresh, long tokenId) {
        Token token = findTokenById(tokenId);
        token.setRefresh(refresh);
        return tokenRepo.save(token);
    }

    public void deleteTokenById(long tokenId) {
        tokenRepo.deleteById(tokenId);
    }
}
