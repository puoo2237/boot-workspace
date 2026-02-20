package com.ex01.basic.controller;

import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.dto.MemberRegDto;
import com.ex01.basic.dto.post.PageDto;
import com.ex01.basic.service.MemberFileService;
import com.ex01.basic.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Tag(name = "MemberAPI", description = "회원 도메인 API")
// controller -> service -> repository -> dto
@RestController
@RequestMapping("/members")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberFileService memberFileService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(
            summary = "회원 이미지 조회",
            description = "프로필의 이미지 다운로드"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "이미지 조회 성공",
                    content = @Content(
                            mediaType = "image/*",
                            schema = @Schema(implementation = Byte.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "이미지 없음"
            )
    }
    )
    @GetMapping("{fileName}/image")
    public ResponseEntity<byte[]> getMeberImage(@PathVariable String fileName) {
        byte[] imageByte = null;
        imageByte = memberFileService.getImage(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                .body(imageByte);
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
                            array = @ArraySchema(schema = @Schema(implementation = PageDto.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Void.class))
                    )
            )
    }
    )
    public ResponseEntity<HashMap<String, Object>> getList(
            @RequestParam(value = "start", defaultValue = "0") int start) {
        return ResponseEntity.ok(memberService.getList(start));
    }
    @SecurityRequirement(name = "JWT")
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
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            )
    }
    )
    public ResponseEntity<MemberDto> getOne(@PathVariable int id,
                                            Authentication authentication) {
        return ResponseEntity.ok(memberService.getOne(id, authentication.getName()));
    }

    @SecurityRequirement(name = "JWT")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @RequestParam(value = "file", required = false) MultipartFile multipartFile,
            @ParameterObject @ModelAttribute MemberRegDto memberRegDto,
            Authentication authentication) {
        memberService.update(id, memberRegDto, multipartFile, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @SecurityRequirement(name = "JWT")
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
    public ResponseEntity<Void> delete(@PathVariable int id,
                                       @RequestBody(required = false) String fileName,
                                       Authentication authentication) {
        memberService.delete(id, fileName, authentication.getName());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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
    public ResponseEntity<Void> insert(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                       @ParameterObject @ModelAttribute
                                       MemberRegDto memberRegDto) {
        System.out.println("memberRegDto: " + memberRegDto);
        System.out.println("multipartFile: " + multipartFile);
        memberService.insert(memberRegDto, multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
