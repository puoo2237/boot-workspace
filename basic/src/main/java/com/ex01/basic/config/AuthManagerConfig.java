package com.ex01.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration // Bean을 만들어서 등록하고 싶을 때 사용
// AuthenticationManager(인증 처리)를 Bean으로 꺼내기 위한 클래스
public class AuthManagerConfig {
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception{
        return config.getAuthenticationManager();
    }
}


