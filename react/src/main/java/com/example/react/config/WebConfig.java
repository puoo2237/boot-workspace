package com.example.react.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // addMapping: 모든 엔드포인트 허용
        // 컨트롤러들의 모든 경로들
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // https://editor.swagger.io/
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(true); // 쿠키 전달 허용

    }
}
