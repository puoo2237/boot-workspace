package com.ex01.basic.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String secretKey;
    private final long expirationMs = 1000 * 60 * 30; // 30분동안 유효

    // 서버 -> 클라이언트
    public String generateToken(String username, String role) {
        // JWT의 payload 부분 (사용자 정보가 들어있는 영역)
        // 사용자 정보; {식별자, 만료 시간, 발급 시간, 사용자 권한, 이메일, 커스텀 정보}
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    // 클라이언트 -> 서버
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(secretKey)
//                    .parseClaimsJws(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public String getUsernameFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
