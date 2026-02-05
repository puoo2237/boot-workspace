package com.example.react.config;

import com.example.react.dto.MemberDto;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public MemberDto memberDto() {
        return new MemberDto();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("연습 OpenAPI")
                        .description("스웨거 연습")
                        .version("v1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:10000")
                                .description("개발용 서버 주소")));
    }
}
