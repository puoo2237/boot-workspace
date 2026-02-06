package com.ex01.basic.controller;

import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.dto.MemberRegDto;
import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "MemberAPI", description = "회원 도메인 API")
// controller -> service -> repository -> dto
@RestController
@RequestMapping("/members")
public class MemberController {
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

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
    public ResponseEntity<Page<MemberEntity>> getList(
            @RequestParam(name="start", defaultValue = "0") int start) {
        return ResponseEntity.ok(memberService.getList(start));
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
        return ResponseEntity.ok(memberService.getOne(id));
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
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "내용 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            )
    }
    )
    public ResponseEntity<Void> modify(@PathVariable int id,
                                       @RequestBody MemberRegDto memberRegDto) {
        memberService.modify(id, memberRegDto);
        return ResponseEntity.ok().build();
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
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "204",
                    description = "내용 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            )
    }
    )
    public ResponseEntity<Void> delMember(@PathVariable int id) {
        memberService.delMember(id);
        return ResponseEntity.ok().build();
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
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "201",
                    description = "회원 등록",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            )
    }
    )
    public ResponseEntity<Void> insert(@RequestBody MemberRegDto memberRegDto) {
        memberService.insert(memberRegDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
                                    schema = @Schema(implementation = Void.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "로그인 실패",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    )
            }
    )
    public ResponseEntity<Void> login(@RequestBody LoginDto loginDto) {
        memberService.login(loginDto);
        return ResponseEntity.ok().build();
    }

}
