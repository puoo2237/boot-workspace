package com.example.jwt_test.config;

import com.example.jwt_test.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
//        System.out.println("filter 실행 확인");
//        System.out.println("header: " + request.getHeader(HttpHeaders.AUTHORIZATION));
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                String uName = jwtUtil.getUsernameFormToken(token);
//                System.out.println(jwtUtil.validateToken(token));
                if (uName != null) {
                    UserDetails userDetails = authService.loadUserByUsername(uName);
                    System.out.println("role: " + userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, // 사용자 정보
                            null, // 비밀번호
                            userDetails.getAuthorities() // 역할
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }


        String testName = "testname";
        filterChain.doFilter(request, response);
    }
}
