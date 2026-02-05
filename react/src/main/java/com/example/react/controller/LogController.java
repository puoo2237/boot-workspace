package com.example.react.controller;

import com.example.react.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/log")
public class LogController {
    @Autowired(required = false)
    private MemberDto memberDto;

    @GetMapping("print")
    public ResponseEntity<Void> logPrint() {
        System.out.println("dto: " + memberDto);
        log.debug("debug message");
        log.debug("info message");
        log.debug("warn message");
        log.debug("error message");
        return ResponseEntity.ok().build();
    }
}
