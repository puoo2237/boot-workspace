package com.example.react.controller;

import com.example.react.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "MemberAPI", description = "회원 도메인 API")
@RestController
@RequestMapping("api/v1")
public class SwaggerController {
    List<MemberDto> DB = new ArrayList<>();

    @GetMapping("members")
    @Operation(
            summary = "모든 사용자 Read",
            description = "회원 목록 조회")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = MemberDto.class)))),
                    @ApiResponse(
                            responseCode = "204",
                            description = "사용자 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "[]")))
            }
    )
    public ResponseEntity<List<MemberDto>> apiTest() {
        if (DB.isEmpty()) {

            DB.add(new MemberDto(0, "aaa", "aaa", "a-role"));
            DB.add(new MemberDto(1, "bbb", "bbb", "b-role"));
            DB.add(new MemberDto(2, "ccc", "ccc", "c-role"));
        }
        return ResponseEntity.ok(DB);
    }

    @GetMapping("members/{id}")
    @Operation(
            summary = "특정 사용자 Read",
            description = "사용자의 id를 파라미터로 전달")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MemberDto.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "null")))
            }
    )

    public ResponseEntity<MemberDto> getOne(@PathVariable int id) {
        if (id == 0)
            return ResponseEntity.ok(new MemberDto(0, "aaa", "aaa", "a-role"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping("members/one")
    public ResponseEntity<MemberDto> getOne2(@RequestParam("id") int id) {
        if (id == 0)
            return ResponseEntity.ok(new MemberDto(0, "aaa", "aaa", "a-role"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("members")
    @Operation(
            summary = "사용자 추가",
            description = "사용자를 추가합니다.")
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "추가 성공")
                            )),
                    @ApiResponse(
                            responseCode = "409",
                            description = "존재하는 사용자",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(value = "동일 id 존재")))
            }
    )
    public ResponseEntity<String> insert(@ParameterObject @ModelAttribute MemberDto memberDto) {
        if (memberDto.getId() == 0)
            return ResponseEntity.status(HttpStatus.CONFLICT).body("중복 id");

        DB.add(memberDto);
        return ResponseEntity.ok("추가 성공");
    }
}
