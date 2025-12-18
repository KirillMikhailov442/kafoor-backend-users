package dev.kafoor.users.security;

import dev.kafoor.users.entity.enums.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JWTService {
    @Value("${token.access.secret}")
    private String accessKey;

    @Value("${token.access.lifetime}")
    private int accessLifeTime;

    @Value("${token.refresh.secret}")
    private String refreshKey;

    @Value("${token.refresh.lifetime}")
    private int refreshLifeTime;

    private static final Logger LOGGER = LogManager.getLogger(JWTService.class);

    private String generateToken(UserPrincipal user, Map<String, Object> extraClaims, String key, int lifeTime){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + lifeTime))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateToken(UserPrincipal user, String key, int lifeTime){
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + lifeTime))
                .signWith(getKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(UserPrincipal user, Map<String, Object> extraClaims, Token tokenType){
        if(tokenType.equals(Token.ACCESS)){
            return generateToken(user, extraClaims, accessKey, accessLifeTime);
        }
        else{
            return generateToken(user, extraClaims, refreshKey, refreshLifeTime);
        }
    }

    public String generateToken(UserPrincipal user, Token tokenType){
        if(tokenType.equals(Token.ACCESS)) return generateToken(user, accessKey, accessLifeTime);
        else return generateToken(user, refreshKey, refreshLifeTime);
    }

    public Claims getClaimsFromToken(String token, Token tokenType){
        try{
            String key;
            if(tokenType.equals(Token.ACCESS)) key = accessKey;
            else key = refreshKey;

            return Jwts.parserBuilder()
                    .setSigningKey(getKey(key))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException expEx) {
            LOGGER.error("Expired JwtException",expEx);
        }catch (UnsupportedJwtException expEx) {
            LOGGER.error("Unsupported JwtException",expEx);
        } catch (MalformedJwtException expEx) {
            LOGGER.error("Malformed JwtException",expEx);
        } catch (SecurityException expEx) {
            LOGGER.error("Security exception",expEx);
        } catch (Exception expEx) {
            LOGGER.error("Invalid token",expEx);
        }
        return null;
    }

    public Date getExpirationDateFromToken(String token, Token tokenType){
        return getClaimsFromToken(token, tokenType).getExpiration();
    }

    public boolean isTokenExpired(String token, Token tokenType){
        return  getExpirationDateFromToken(token, tokenType).before(new Date());
    }

    public String getUsernameFromToken(String token, Token tokenType){
        return getClaimsFromToken(token, tokenType).getSubject();
    }

    public boolean validateToken(String token, Token tokenType, UserPrincipal user){
        try{
            final String username = getUsernameFromToken(token, tokenType);
            return (username.equals(user.getUsername()) && isTokenExpired(token, tokenType));
        } catch (Exception e) {
            return false;
        }
    }

    private Key getKey(String key){
        byte[] KeyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
}
