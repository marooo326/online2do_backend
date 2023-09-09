package com.marooo.todo.auth.provider;

import com.marooo.todo.auth.dto.Jwt;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private final Key secretKey;
    private final Date accessExpirationDate;
    private final Date refreshExpirationDate;

    public JwtProvider(@Value("${jwt.secretKey}") String secretKey) {
        this.accessExpirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 30); // 30분
        this.refreshExpirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3); // 3일
        byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        this.secretKey = Keys.hmacShaKeyFor(secretByteKey);
    }

    public Jwt createToken(Authentication authentication) {
        return Jwt.builder()
                .grantType("Bearer")
                .accessToken(createAccessToken(authentication))
                .refreshToken(createRefreshToken(authentication))
                .build();
    }

    public String createAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessExpirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(refreshExpirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        } catch (Exception e) {
            log.info("오류가 발생했습니다.", e);
        }
        return false;
    }

    public Claims parseClaims(String token) {
        Jws<Claims> jws = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        return jws.getBody();
    }
}
