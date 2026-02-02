package com.ex01.basic.controller;

import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.exception.MemberDuplicationException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller -> service -> repository -> dto
@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

//    public MemberController() {
//        System.out.println("member ctrl 생성자");
//    }

    @GetMapping("/test")
    public ResponseEntity<String> getTest() {
        System.out.println("service: " + memberService);
        memberService.serviceTest();
        return ResponseEntity.ok("성공");
    }

    @GetMapping
    public ResponseEntity<List<MemberDto>> getList() {
        List<MemberDto> list = null;
        try {
            list = memberService.getList();
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(list);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberDto> getOne(@PathVariable int id) {
        MemberDto memberDto;
        try {
            memberDto = memberService.getOne(id);

        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(memberDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> modify(@PathVariable int id, @ModelAttribute MemberDto memberDto) {
        try {
            memberService.modify(id, memberDto);
        } catch (MemberNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delMember(@PathVariable int id) {
        try {
            memberService.delMember(id);
        } catch (MemberNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<String> insert(@ModelAttribute MemberDto memberDto) {
        try {
            memberService.insert(memberDto);
        } catch (MemberDuplicationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("존재하는 id");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("추가 성공");
    }
}
