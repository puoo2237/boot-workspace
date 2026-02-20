package com.ex01.basic.exception.post;

public class PostDuplicationException extends RuntimeException {
    public PostDuplicationException() {
    }

    public PostDuplicationException(String msg) {
        super(msg);
    }
}
