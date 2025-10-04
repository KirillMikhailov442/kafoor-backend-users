package kafoor.users.user_service.services;

import kafoor.users.user_service.dtos.TokenCreateDTO;
import kafoor.users.user_service.exceptions.NotFound;
import kafoor.users.user_service.models.Token;
import kafoor.users.user_service.models.User;
import kafoor.users.user_service.repositories.TokenRepo;
import kafoor.users.user_service.utils.jwt.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {
    @Autowired
    private TokenRepo tokenRepo;
    @Autowired
    private JWTUtils jwtUtils;

    public Token findTokenById(long id){
        return tokenRepo.findById(id)
                .orElseThrow(() -> new NotFound("Token not found with such id"));
    }

    public Token findTokenByUserIdAndUserAgentAndIP(long userId, String userAgent, String ip){
        return tokenRepo.findByUserIdAndUserAgentAndIp(userId, userAgent, ip)
                .orElseThrow(() -> new NotFound("Token not found"));
    }

    public Token findTokenByUserAndUserAgentAndIP(User user, String userAgent, String ip){
        return tokenRepo.findByUserAndUserAgentAndIp(user, userAgent, ip)
                .orElseThrow(() -> new NotFound("Toke not found"));
    }

    public Token findTokenByRefresh(String refreshToken){
        return tokenRepo.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFound("Token not found with such refresh token"));
    }

    public Token findTokenByUserId(long userId){
        List<Token> tokens = tokenRepo.findAllByUserId(userId);
        if(tokens.isEmpty()) throw new NotFound("Tokens not found with such user id");
        return tokens.getFirst();
    }

    public String getRefreshFromTokens(long id){
        return findTokenById(id).getRefreshToken();
    }

    public String getIPFromTokens(long id){
        return findTokenById(id).getIp();
    }

    public String getUserAgentFromTokens(long id){
        return findTokenById(id).getUserAgent();
    }

    public Token createToken(TokenCreateDTO dto, User user){
        Token token = Token.builder()
                .user(user)
                .refreshToken(dto.getRefresh())
                .ip(dto.getIP())
                .userAgent(dto.getUserAgent()).build();
        return tokenRepo.save(token);
    }

    public Token updateToken(TokenCreateDTO dto, long tokenId){
        Token token = findTokenById(tokenId);

        if(dto.getIP() != null) token.setIp(dto.getIP());
        if(dto.getRefresh() != null) token.setRefreshToken(dto.getRefresh());
        if(dto.getUserAgent() != null) token.setUserAgent(dto.getUserAgent());
        return tokenRepo.save(token);
    }

    public Token updateRefreshToken(String refresh, long tokenId){
        Token token = findTokenById(tokenId);
        token.setRefreshToken(refresh);
        return tokenRepo.save(token);
    }

    public void deleteTokenById(long tokenId){
        tokenRepo.deleteById(tokenId);
    }
}
