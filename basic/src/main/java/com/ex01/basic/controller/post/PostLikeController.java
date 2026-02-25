package com.ex01.basic.controller.post;

import com.ex01.basic.config.security.CustomUserDetails;
import com.ex01.basic.service.post.PostLikeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/posts/{id}/like")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> likePost(
            @PathVariable int id,
            @AuthenticationPrincipal CustomUserDetails details
    ) {
        postLikeService.likePost(id, details.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/posts/{id}/like")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<Void> unlikePost(
            @PathVariable int id,
            @AuthenticationPrincipal CustomUserDetails details
    ) {
        postLikeService.unlikePost(id, details.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
