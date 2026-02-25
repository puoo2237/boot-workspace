package com.example.jwt_test.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;
    private final long expirationMs = 1000 * 60; // 1분 동안 유효

    public String generateToken(String username) {
        System.out.println("jwt secretKey: " + secretKey);
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("name", "추가 이름");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis())) // 토큰 생성 시간
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFormToken(String token){
        Claims claims=getClaims(token);
        System.out.println("username: "+ claims.get("username", String.class));
        System.out.println("name: "+ claims.get("name", String.class));
        return claims.get("username", String.class);
    }
}
