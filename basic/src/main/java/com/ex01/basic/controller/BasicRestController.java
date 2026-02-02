package com.ex01.basic.controller;

import com.ex01.basic.dto.BasicDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BasicRestController {
    private List<BasicDto> list;

    public BasicRestController() {
        list = new ArrayList<BasicDto>();
        for (int i = 0; i < 3; i++) {
            list.add(new BasicDto("aaa" + i, "test", i));
        }
        System.out.println("ctrl 생성자 실행");
    }

    @GetMapping("/members")
    public ResponseEntity<List<BasicDto>> getMembers() {
        return ResponseEntity.ok(list);
    }

    @GetMapping("/members/{username}")
    public ResponseEntity<BasicDto> getMembers(@PathVariable("username") String username) {
        BasicDto basicDto = list
                .stream()
                .filter(mem -> mem.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (basicDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 리턴값이 없을 때
        }
        return ResponseEntity.ok(basicDto);
    }

    @PostMapping("/members")
    public ResponseEntity<String> postMembers(@ModelAttribute BasicDto basicDto) {
        boolean bool = list.add(basicDto);
        if (bool)
            return ResponseEntity.status(HttpStatus.CREATED).body("추가 성공"); //201
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("문제 발생: 잘못된 요청"); //400
    }

    @PostMapping("/members/form")
    public ResponseEntity<String> postMembersForm(@ModelAttribute BasicDto basicDto) {
        boolean bool = list.add(basicDto);
        if (bool)
            return ResponseEntity.status(HttpStatus.CREATED).build(); //201
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("문제 발생: 잘못된 요청"); //400
    }

    @PutMapping("/members")
    public ResponseEntity<Void> putMembers(@ModelAttribute BasicDto basicDto) {
        boolean bool = list.stream()
                .filter(mem -> mem.getUsername().equals(basicDto.getUsername()))
                .findFirst()
                .map(mem -> {
                    mem.setPassword(basicDto.getPassword());
                    mem.setNum(basicDto.getNum());
                    return true;
                })
                .orElse(false);

        if (bool) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/members/{username}")
    public ResponseEntity<Void> delMEmbers(@PathVariable(name = "username") String username) {
        boolean bool = list.removeIf(mem -> mem.getUsername().equals(username));
        if (bool)
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
    }

    //    데이터 요청
    @GetMapping("/test01")
    public ResponseEntity<String> getTest01() {
        return ResponseEntity.ok("get 통신성공");
    }

    // get(/)
    @GetMapping("/test01/{username}")
    public ResponseEntity<String> getTest01(@PathVariable("username") String un) {
        un += "님의 정보 검색\n";
        return ResponseEntity.ok(un + "get 통신성공");
    }

    // get(?)
    @GetMapping("val")
    public ResponseEntity<String> getTest02(@RequestParam("username") String un) {
        un += "님의 정보 검색\n";
        return ResponseEntity.ok(un + "get 통신성공");
    }

    //    데이터 추가
    @PostMapping("/test01")
    public ResponseEntity<String> postTest01() {
        return ResponseEntity.ok("post 통신성공");
    }

    //    데이터 수정
    @PutMapping("/test01")
    public ResponseEntity<String> putTest01() {
        return ResponseEntity.ok("put 통신성공");
    }

    //    데이터 삭제
    @DeleteMapping("/test01")
    public ResponseEntity<String> deleteTest01() {
        return ResponseEntity.ok("delete 통신성공");
    }

    @GetMapping("/test02")
    public ResponseEntity<List<String>> getTest02() {
        ArrayList<String> list = new ArrayList<>();
        list.add("aaa");
        list.add("bbb");
        return ResponseEntity.ok(list);
    }
}
