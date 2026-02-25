package com.example.jwt_test.controller;

import com.example.jwt_test.dto.MemberDto;
import com.example.jwt_test.dto.MemberRegDto;
import com.example.jwt_test.service.MemberService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberDto>> getList() {
        return ResponseEntity.ok(memberService.getList());
    }

    @PostMapping
    public ResponseEntity<Void> register(
            @ParameterObject @ModelAttribute MemberRegDto memberRegDto) {
        memberService.register(memberRegDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       Authentication authentication) {
        System.out.println("삭제 이름: " + authentication.getName());
        System.out.println("삭제 인증 정보: " + authentication.getPrincipal());
        memberService.delete(id, authentication.getName());
        return ResponseEntity.ok().build();
    }
}
