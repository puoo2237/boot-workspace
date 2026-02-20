package com.example.db_test.controller.post;

import com.example.db_test.dto.post.PostAllDto;
import com.example.db_test.dto.post.PostDetailDto;
import com.example.db_test.dto.post.PostDto;
import com.example.db_test.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<String> insert(
            @ParameterObject @ModelAttribute PostDto postDto
    ) {
        postService.insert(postDto);
        return ResponseEntity.ok("데이터 추가");
    }

    @GetMapping
    public ResponseEntity<List<PostAllDto>> getPost(){
        return ResponseEntity.ok(postService.getPost());
    }

    @GetMapping("{id}")
    public ResponseEntity<PostDetailDto> getPostOne(
            @PathVariable Long id,
            @RequestParam("number") Long number
    ){
       return ResponseEntity.ok(postService.getPostOne(id, number));
    }
}
