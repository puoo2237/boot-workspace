package com.example.db_test.exception.post;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String msg) {
        super(msg);
    }
}
