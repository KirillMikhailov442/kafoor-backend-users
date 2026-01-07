package dev.kafoor.users.service;

import dev.kafoor.users.dto.v1.internal.TokenCreate;
import dev.kafoor.users.entity.TokenEntity;
import dev.kafoor.users.entity.UserEntity;
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

    public List<TokenEntity> findAllTokens() {
        return tokenRepo.findAll();
    }

    public List<TokenEntity> findTokensByUserId(long userId) {
        return tokenRepo.findAllByUserId(userId);
    }

    public Optional<TokenEntity> findTokenById(long id) {
        return tokenRepo.findById(id);
    }

    public TokenEntity findTokenByIdOrThrow(long id) {
        return tokenRepo.findById(id)
                .orElseThrow(() -> new NotFound("token not found with such id"));
    }

    public Optional<TokenEntity> findTokenByRefresh(String refresh) {
        return tokenRepo.findByRefresh(refresh);
    }

    public TokenEntity findTokenByRefreshOrThrow(String refresh) {
        return tokenRepo.findByRefresh(refresh).orElseThrow(() -> new NotFound("token not found"));
    }

    public Optional<TokenEntity> findTokenByUserIdAndUserAgentAndIp(long userId, String userAgent, String ip) {
        return tokenRepo.findByUserIdAndUserAgentAndIp(userId, userAgent, ip);
    }

    public TokenEntity findTokenByUserIdAndUserAgentAndIpOrThrow(long userId, String userAgent, String ip) {
        return tokenRepo.findByUserIdAndUserAgentAndIp(userId, userAgent, ip)
                .orElseThrow(() -> new NotFound("token not found"));
    }

    public Optional<TokenEntity> findTokenByUserAndUserAgentAndIp(UserEntity userEntity, String userAgent, String ip) {
        return tokenRepo.findByUserAndUserAgentAndIp(userEntity, userAgent, ip);
    }

    public TokenEntity findTokenByUserAndUserAgentAndIpOrThrow(UserEntity userEntity, String userAgent, String ip) {
        return tokenRepo.findByUserAndUserAgentAndIp(userEntity, userAgent, ip)
                .orElseThrow(() -> new NotFound("token not found"));
    }

    public TokenEntity createToken(TokenCreate dto, UserEntity userEntity) {
        TokenEntity tokenEntity = TokenEntity.builder()
                .user(userEntity)
                .refresh(dto.getRefresh())
                .ip(dto.getIp())
                .userAgent(dto.getUserAgent()).build();
        return tokenRepo.save(tokenEntity);
    }

    public TokenEntity updateToken(TokenCreate dto, long tokenId) {
        TokenEntity tokenEntity = findTokenByIdOrThrow(tokenId);

        if (dto.getIp() != null)
            tokenEntity.setIp(dto.getIp());
        if (dto.getRefresh() != null)
            tokenEntity.setRefresh(dto.getRefresh());
        if (dto.getUserAgent() != null)
            tokenEntity.setUserAgent(dto.getUserAgent());
        return tokenRepo.save(tokenEntity);
    }

    public TokenEntity updateRefreshToken(String refresh, long tokenId) {
        TokenEntity tokenEntity = findTokenByIdOrThrow(tokenId);
        tokenEntity.setRefresh(refresh);
        return tokenRepo.save(tokenEntity);
    }

    public void deleteTokenById(long tokenId) {
        tokenRepo.deleteById(tokenId);
    }
}
