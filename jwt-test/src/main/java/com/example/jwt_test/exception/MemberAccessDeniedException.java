package com.example.jwt_test.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberAccessDeniedException extends RuntimeException {
    public MemberAccessDeniedException(String msg) {
        super(msg);
    }
}
