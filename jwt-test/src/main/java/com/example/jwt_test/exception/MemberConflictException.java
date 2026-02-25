package com.example.jwt_test.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberConflictException extends RuntimeException {
    public MemberConflictException(String msg) {
        super(msg);
    }
}
