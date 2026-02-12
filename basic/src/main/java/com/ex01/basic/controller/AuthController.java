package com.ex01.basic.controller;

import com.ex01.basic.config.JwtUtil;
import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.exception.InvalidPasswordException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Tag(name = "LoginAPI", description = "로그인 도메인 API")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "로그인 인증"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 실패",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    )
            }
    )
    public ResponseEntity<Map<String, String>> login(
            @ParameterObject @ModelAttribute LoginDto loginDto
    ) {
        // 로그인 인증 (서버 내부)
        // 사용자가 로그인 폼에 입력한 아이디/비밀번호를 담는 객체
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), // principal(username)
                loginDto.getPassword() // credentials(password)
        );

        Authentication authentication = null;
        // 인증 완료 객체
        try {
            authentication = authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            throw new InvalidPasswordException("비밀번호 불일치");
        }
        // 로그인 증명서 발급 (클라이언트 <-> 서버용)
        // DB에서 가져온 “사용자 정보”를 담는 객체
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails
                .getAuthorities()
                .iterator()
                .next()
                .getAuthority();
        String resultToken = jwtUtil.generateToken(
                userDetails.getUsername(),
                role);
        return ResponseEntity.ok(Collections.singletonMap("token", resultToken));

    }
}
