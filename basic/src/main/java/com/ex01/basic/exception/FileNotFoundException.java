package com.ex01.basic.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String msg) {
        super(msg);
    }
}
