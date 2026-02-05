package com.example.db_test.controller;

import com.example.db_test.dto.MemberAllDto;
import com.example.db_test.dto.MemberModifyDto;
import com.example.db_test.dto.MemberRegDto;
import com.example.db_test.entity.MemberEntity;
import com.example.db_test.exception.MemberNotFoundException;
import com.example.db_test.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<List<MemberAllDto>> gitList(
            @RequestParam(name = "start", defaultValue = "0") int start
    ) {
        System.out.println("start: +start");
        return ResponseEntity.ok(memberService.getList(start));

    }

    @GetMapping("/members/{userId}")
    public ResponseEntity<MemberAllDto> getMember(@PathVariable String userId) {
        MemberAllDto memberAllDto = memberService.getMember(userId);
        return ResponseEntity.ok(memberAllDto);

    }

    @PostMapping("/members")
    public ResponseEntity<Void> insert(@ParameterObject @ModelAttribute MemberRegDto memberRegDto) {
//        System.out.println("ctrl memberEntity: " + memberEntity);
        memberService.insert(memberRegDto);
        return ResponseEntity.ok().build();
    }


    @DeleteMapping("/members/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        memberService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PutMapping("/member/{id}")
    public ResponseEntity<Void> modify(@PathVariable long id,
                                       @ParameterObject @ModelAttribute MemberModifyDto memberModifyDto) {
        memberService.modify(id, memberModifyDto);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/member/test/{number}")
    public ResponseEntity<MemberEntity> getTestMember(@PathVariable long number) {
        MemberEntity memberEntity = memberService.getTestMember(number);
        return ResponseEntity.ok(memberEntity);
    }


}
