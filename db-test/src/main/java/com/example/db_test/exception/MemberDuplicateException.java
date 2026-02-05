package com.example.db_test.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberDuplicateException extends RuntimeException {
    public MemberDuplicateException(String msg) {
        super(msg);
    }
}
