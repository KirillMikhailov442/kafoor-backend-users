package kafoor.users.user_service.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import kafoor.users.user_service.models.enums.Tokens;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtils {
    @Value("${token.access.secret}")
    private String accessKey;

    @Value("${token.access.lifetime}")
    private int accessLifeTime;

    @Value("${token.refresh.secret}")
    private String refreshKey;

    @Value("${token.refresh.lifetime}")
    private int refreshLifeTime;

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

    public String generateToken(UserPrincipal user, Map<String, Object> extraClaims, Tokens tokenType){
        if(tokenType.equals(Tokens.ACCESS_TOKEN)){
            return generateToken(user, extraClaims, accessKey, accessLifeTime);
        }
        else{
            return generateToken(user, extraClaims, refreshKey, refreshLifeTime);
        }
    }

    public String generateToken(UserPrincipal user, Tokens tokenType){
        if(tokenType.equals(Tokens.ACCESS_TOKEN)){
            return generateToken(user, accessKey, accessLifeTime);
        }
        else{
            return generateToken(user, refreshKey, refreshLifeTime);
        }
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsTFunction, Tokens tokenType){
        final Claims claims = getClaimsFromToken(token, tokenType);
        return claimsTFunction.apply(claims);
    }

    public Claims getClaimsFromToken(String token, Tokens tokenType){
        String key;
        if(tokenType.equals(Tokens.ACCESS_TOKEN))
            key = accessKey;
        else key = refreshKey;

        return Jwts.parser().setSigningKey(getKey(key)).parseClaimsJws(token).getBody();
    }

    public Date getExpirationDateFromToken(String token, Tokens tokenType){
        return getClaimsFromToken(token, tokenType).getExpiration();
    }

    public boolean isTokenExpired(String token, Tokens tokenType){
        return  getExpirationDateFromToken(token, tokenType).before(new Date());
    }

    public String getUsernameFromToken(String token, Tokens tokenType){
        return getClaimsFromToken(token, tokenType).getSubject();
    }

    public boolean validateToken(String token, Tokens tokenType, UserPrincipal user){
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
