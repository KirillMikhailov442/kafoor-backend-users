package dev.kafoor.users.service;

import dev.kafoor.users.dto.internal.TokenCreate;
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

    public Optional<Token> findTokenById(long id) {
        return tokenRepo.findById(id);
    }

    public Token findTokenByIdOrThrow(long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new NotFound("Token not found with such id"));
    }

    public Optional<Token> findTokenByUserIdAndUserAgentAndIp(long userId, String userAgent, String ip) {
        return tokenRepo.findByUserIdAndUserAgentAndIp(userId, userAgent, ip);
    }

    public Token findTokenByUserIdAndUserAgentAndIpOrThrow(long userId, String userAgent, String ip) {
        return tokenRepo.findByUserIdAndUserAgentAndIp(userId, userAgent, ip)
                .orElseThrow(() -> new NotFound("Token not found"));
    }

    public Optional<Token> findTokenByUserAndUserAgentAndIp(User user, String userAgent, String ip) {
        return tokenRepo.findByUserAndUserAgentAndIp(user, userAgent, ip);
    }

    public Token findTokenByUserAndUserAgentAndIpOrThrow(User user, String userAgent, String ip) {
        return tokenRepo.findByUserAndUserAgentAndIp(user, userAgent, ip)
                .orElseThrow(() -> new NotFound("Token not found"));
    }

    public Token createToken(TokenCreate dto, User user){
        Token token = Token.builder()
                .user(user)
                .refresh(dto.getRefresh())
                .ip(dto.getIp())
                .userAgent(dto.getUserAgent()).build();
        return tokenRepo.save(token);
    }

    public Token updateToken(TokenCreate dto, long tokenId) {
        Token token = findTokenByIdOrThrow(tokenId);

        if (dto.getIp() != null)
            token.setIp(dto.getIp());
        if (dto.getRefresh() != null)
            token.setRefresh(dto.getRefresh());
        if (dto.getUserAgent() != null)
            token.setUserAgent(dto.getUserAgent());
        return tokenRepo.save(token);
    }

    public Token updateRefreshToken(String refresh, long tokenId) {
        Token token = findTokenByIdOrThrow(tokenId);
        token.setRefresh(refresh);
        return tokenRepo.save(token);
    }

    public void deleteTokenById(long tokenId) {
        tokenRepo.deleteById(tokenId);
    }
}
