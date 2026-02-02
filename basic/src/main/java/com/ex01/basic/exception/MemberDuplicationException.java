package com.ex01.basic.exception;

public class MemberDuplicationException extends RuntimeException {
    public MemberDuplicationException() {
    }

    public MemberDuplicationException(String msg) {
        super(msg);
    }
}
