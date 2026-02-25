package com.example.jwt_test.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String msg) {
        super(msg);
    }
}
