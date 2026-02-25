package com.example.jwt_test.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name="JWT")
public class AdminController {
    @GetMapping
    public String index(){
        return "admin get만 접속 가능";
    }
    @PostMapping
    public String post(){
        return "admin post만 접속 가능";
    }
}
