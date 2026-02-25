package com.example.jwt_test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalMemberException {
    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlerNotFound(
            MemberNotFoundException memberNotFoundException) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setDetail(memberNotFoundException.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }


    @ExceptionHandler(MemberConflictException.class)
    public ResponseEntity<ProblemDetail> handlerConflictException(
            MemberConflictException memberConflictException) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setDetail(memberConflictException.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(MemberAccessDeniedException.class)
    public ResponseEntity<ProblemDetail> handlerAccessDeniedException(
            MemberAccessDeniedException memberAccessDeniedException) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        problemDetail.setDetail(memberAccessDeniedException.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(problemDetail);
    }
}
