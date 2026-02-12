package com.ex01.basic.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberAccessDeniedException extends RuntimeException {
    public MemberAccessDeniedException(String msg) {
        super(msg);
    }
}