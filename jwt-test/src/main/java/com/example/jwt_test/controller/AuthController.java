package com.example.jwt_test.controller;

import com.example.jwt_test.config.JwtUtil;
import com.example.jwt_test.dto.MemberRegDto;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @ParameterObject @ModelAttribute MemberRegDto memberRegDto
    ) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                memberRegDto.getUsername(),
                memberRegDto.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(token);
//        System.out.println("인증된 사용자 정보: " + authentication.getPrincipal());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 인증 성공이라면 토큰 발급
        String resultToken = jwtUtil.generateToken(userDetails.getUsername());
//        System.out.println("name: " + authentication.getName());
//        System.out.println("name: " + userDetails.getUsername());
//        System.out.println("auth: " + userDetails.getAuthorities());
//        System.out.println(resultToken);

//        Map<String, String> map =new HashMap<>();
//        map.put("token", resultToken);
        return ResponseEntity.ok(Collections.singletonMap("token", resultToken));
    }
}
