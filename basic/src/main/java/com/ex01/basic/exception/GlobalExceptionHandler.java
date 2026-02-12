package com.ex01.basic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ProblemDetail> notFoundHandler(
            MemberNotFoundException memberNotFoundException
    ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("사용자 없음");
        problemDetail.setDetail(memberNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(MemberDuplicationException.class)
    public ResponseEntity<ProblemDetail> notFoundHandler(
            MemberDuplicationException memberDuplicationException
    ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("중복 사용자");
        problemDetail.setDetail(memberDuplicationException.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ProblemDetail> notFoundHandler(
            InvalidLoginException invalidLoginException
    ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("인증 실패");
        problemDetail.setDetail(invalidLoginException.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problemDetail);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ProblemDetail> notFoundHandler(
            FileNotFoundException fileNotFoundException
    ){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("파일이 존재하지 않음");
        problemDetail.setDetail(fileNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }


    @ExceptionHandler(MemberAccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handlerAccessDeniedException(
            MemberAccessDeniedException memberAccessDeniedException) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setDetail(memberAccessDeniedException.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }
}
