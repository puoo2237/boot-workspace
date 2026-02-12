package com.ex01.basic.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AdminAPI", description = "관리자 도메인 API")
@RestController
@RequestMapping("/admin")
@SecurityRequirement(name="JMT")
public class AdminController {

    @GetMapping
    public String index() {
        return "admin get만 접속 가능";
    }
    @PostMapping
    public String post(){
        return "admin post만 접속 가능";
    }
}
