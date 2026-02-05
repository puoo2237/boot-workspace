package com.example.db_test.native_test;


import com.example.db_test.dto.MemberAllDto;
import com.example.db_test.dto.MemberRegDto;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping("contents")
    public ResponseEntity<List<MemberAllDto>> getContents() {
        return ResponseEntity.ok(testService.getContents());
    }

    @GetMapping("contents/{num}")
    public ResponseEntity<MemberAllDto> getContent(
            @PathVariable long num
    ) {
        return ResponseEntity.ok(testService.getContent(num));
    }

    @PostMapping("contents")
    public ResponseEntity<Void> insertContent(
            @ParameterObject @ModelAttribute MemberRegDto memberRegDto) {
        testService.insertContent(memberRegDto);
        return ResponseEntity.ok().build();
    }
}
