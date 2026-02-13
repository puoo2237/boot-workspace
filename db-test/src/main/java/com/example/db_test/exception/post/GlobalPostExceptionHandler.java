package com.example.db_test.exception.post;

import com.example.db_test.exception.post.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(name="PostGlobalExceptionHandler")
public class GlobalExceptionHandler {
    @ExceptionHandler(com.example.db_test.exception.MemberNotFoundException.class)
    public ResponseEntity<ProblemDetail> notFoundHandler(
            MemberNotFoundException memberNotFoundException
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("사용자 없음");
        problemDetail.setDetail(memberNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problemDetail);
    }

}
