package com.ex01.basic.controller;

import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.exception.MemberDuplicationException;
import com.ex01.basic.exception.MemberNotFoundException;
import com.ex01.basic.exception.InvalidLoginException;
import com.ex01.basic.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "MemberAPI", description = "회원 도메인 API")
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

//    @GetMapping("/test")
//    public ResponseEntity<String> getTest() {
//        System.out.println("service: " + memberService);
//        memberService.serviceTest();
//        return ResponseEntity.ok("성공");
//    }

    @GetMapping
    @Operation(
            summary = "전체 회원 조회",
            description = "전체 회원 조회"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MemberDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 없음",
                    content = @Content(
                            examples = @ExampleObject(value = "[]")
                    )
            )
    }
    )
    public ResponseEntity<List<MemberDto>> getList() {
        List<MemberDto> list = null;
        try {
            list = memberService.getList();
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(list);
        }
        System.out.println(list);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "특정 회원 조회",
            description = "id를 이용한 회원 조회"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 없음",
                    content = @Content(
                            examples = @ExampleObject(value = "null")
                    )
            )
    }
    )
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
    @Operation(
            summary = "회원 수정",
            description = "id를 이용한 회원 수정"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "내용 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)
                    )
            )
    }
    )
    public ResponseEntity<Integer> modify(@PathVariable int id, @RequestBody MemberDto memberDto) {
        try {
            memberService.modify(id, memberDto);
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(1);
        }
        return ResponseEntity.ok(0);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "회원 삭제",
            description = "id를 이용한 회원 삭제"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "내용 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class)
                    )
            )
    }
    )
    public ResponseEntity<Integer> delMember(@PathVariable int id) {
        try {
            memberService.delMember(id);
        } catch (MemberNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(1);
        }
        return ResponseEntity.ok(0);
    }

    @PostMapping
    @Operation(
            summary = "회원 추가",
            description = "회원 등록"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "409",
                    description = "중복 회원",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "회원 등록",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoginDto.class)
                    )
            )
    }
    )
    public ResponseEntity<Integer> insert(@RequestBody MemberDto memberDto) {
        try {
            memberService.insert(memberDto);
        } catch (MemberDuplicationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(1);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(0);
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "로그인 인증"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 실패",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = LoginDto.class)
                            )
                    )
            }
    )
    public ResponseEntity<Integer> login(@RequestBody LoginDto loginDto) {
        try {
            memberService.login(loginDto);
        } catch (InvalidLoginException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(1);
        }
        return ResponseEntity.ok(0);
    }

}
