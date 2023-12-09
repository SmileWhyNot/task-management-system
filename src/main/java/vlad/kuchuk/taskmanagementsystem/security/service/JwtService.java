package vlad.kuchuk.taskmanagementsystem.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlad.kuchuk.taskmanagementsystem.security.config.JwtConfig;
import vlad.kuchuk.taskmanagementsystem.security.entity.UserEntity;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig jwtConfig;

    public Long extractId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Long userId) {
        return generateToken(new HashMap<>(), userId, jwtConfig.getTokenExpirationMs());
    }

    public String generateToken(Map<String, Object> extraClaims, Long userId, long expirationMs) {
        return Jwts.builder()
                   .setClaims(extraClaims)
                   .setSubject(String.valueOf(userId))
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                   .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                   .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefreshToken(Long userId) {
        return generateToken(new HashMap<>(), userId, jwtConfig.getRefreshTokenExpirationMs());
    }

    public boolean isRefreshTokenNotExpired(String refreshToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(getSignInKey())
                                .build()
                                .parseClaimsJws(refreshToken)
                                .getBody();

            Date expirationDate = claims.getExpiration();
            return !expirationDate.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTokenValid(String token, UserEntity user) {
        final Long id = extractId(token);
        return (id.equals(user.getId())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSignInKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}
