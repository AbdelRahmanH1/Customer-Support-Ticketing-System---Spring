package com.system.customer_support_ticketing_system.auth;

import com.system.customer_support_ticketing_system.config.JwtConfig;
import com.system.customer_support_ticketing_system.entities.User;
import com.system.customer_support_ticketing_system.enums.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@AllArgsConstructor
public class JwtService {
    private final JwtConfig jwtConfig;

    public String generateAccessToken(User user) {
        final long tokenExpiration = jwtConfig.getAccessTokenExpiration();
        return generateToken(user, tokenExpiration);
    }

    public String generateRefreshToken(User user) {
        final long tokenExpiration = jwtConfig.getRefreshTokenExpiration();
        return generateToken(user, tokenExpiration);
    }

    public String generateToken(User user,Long timeExpiration){
        return Jwts
                .builder()
                .subject(user.getId().toString())
                .claim("role", user.getRole())
                .claim("email", user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * timeExpiration))
                .signWith(jwtConfig.getSecretKey())
                .compact();
    }

    public boolean validateToken(String token){
        try {
            var claims = getClaims(token);
            return claims.getExpiration().after(new Date());
        }catch (JwtException e){
            return false;
        }
    }

    public Claims getClaims(String token){
        return Jwts
                .parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserIdFromToken(String token){
        return Long.valueOf(getClaims(token).getSubject());
    }
    public UserRole getRoleFromToken(String token){
        return UserRole.valueOf(getClaims(token).get("role", String.class));
    }
}
