package com.example.bankcards.security.jwt;

import java.util.Date;

import com.example.bankcards.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * Сервис для работы с JWT.
 */
@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtKey;
    @Value("${jwt.term}")
    private int jwtTermMs;

    public String generateJwt(User user) {
        Date create = new Date();
        Date endings = new Date(create.getTime() + jwtTermMs);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole())
                .issuedAt(create)
                .expiration(endings)
                .signWith(Keys.hmacShaKeyFor(jwtKey.getBytes()))// HS256
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(jwtKey.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
