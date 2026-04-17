package neupokoev.com.lockservice.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import neupokoev.com.lockservice.dto.JwtAuthenticationDto;
import neupokoev.com.lockservice.dto.TypeUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public JwtAuthenticationDto generateAuthToken(String username, TypeUser type) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setType(type);
        jwtDto.setAccessToken(generateJwtToken(username, type.name()));
        jwtDto.setRefreshToken(generateRefreshToken(username, type.name()));
        return jwtDto;
    }

    public JwtAuthenticationDto refreshBaseToken(String username, String refreshToken, TypeUser type) {
        JwtAuthenticationDto jwtDto = new JwtAuthenticationDto();
        jwtDto.setType(type);
        jwtDto.setAccessToken(generateJwtToken(username, type.name()));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e) {
            log.error("Expired JwtException ", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JwtException ", e);
        } catch (MalformedJwtException e) {
            log.error("Malformed JwtException ", e);
        } catch (SecurityException e) {
            log.error("Security Exception ", e);
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    public TypeUser getTypeFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return TypeUser.valueOf(claims.get("type", String.class));
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateJwtToken(String username, String type) {
        Date date = Date.from(
                LocalDateTime
                        .now()
                        .plusMinutes(10)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        return Jwts.builder()
                .subject(username)
                .claim("type", type)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private String generateRefreshToken(String username, String type) {
        Date date = Date.from(
                LocalDateTime
                        .now()
                        .plusDays(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        return Jwts.builder()
                .subject(username)
                .claim("type", type)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
