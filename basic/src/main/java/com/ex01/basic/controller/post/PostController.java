package com.ex01.basic.controller.post;

import com.ex01.basic.config.security.CustomUserDetails;
import com.ex01.basic.dto.post.PageDto;
import com.ex01.basic.dto.post.PostAddDto;
import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RequiredArgsConstructor
@Tag(name = "PostAPI", description = "게시글 도메인 API")
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Operation(
            summary = "전체 게시글 조회",
            description = "전체 게시글 조회"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PageDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @GetMapping
    public ResponseEntity<HashMap<String, Object>> getPosts(
            @RequestParam(value = "start", defaultValue = "0") int start,
            @AuthenticationPrincipal CustomUserDetails details
    ) {
        int id = 0;
        if(details != null)
            id = details.getId();
        return ResponseEntity.ok(postService.getPosts(start, id));
    }


    @Operation(
            summary = "게시글 조회",
            description = "게시글 조회"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "200",
                            description = "성공",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PostAllDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<PostAllDto> getPost(
            @PathVariable int id,
            Authentication authentication
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(postService.getPost(id, userDetails.getId()));
    }

    @Operation(
            summary = "게시글 추가",
            description = "게시글 추가"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "204",
                            description = "내용 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @PostMapping
    public ResponseEntity<Void> insert(
            @ParameterObject @ModelAttribute PostAddDto postAddDto,
            Authentication authentication
    ) {
        postService.insert(postAddDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(
            summary = "게시글 수정",
            description = "게시글 수정"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "204",
                            description = "내용 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable int id,
            @ParameterObject @ModelAttribute PostAddDto postAddDto,
            Authentication authentication
    ) {
        postService.update(id, postAddDto, authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "게시글 삭제",
            description = "게시글 삭제"
    )
    @ApiResponses(
            {
                    @ApiResponse(
                            responseCode = "204",
                            description = "내용 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "게시글 없음",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Void.class)
                            )
                    )
            }
    )
    @SecurityRequirement(name = "JWT")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id,
            Authentication authentication
    ) {
        postService.delete(id, authentication.getName());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
