package com.example.QuanLyPhongGym.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {

    private String error;

    private Object content;

    public CustomException(String error, String message, Object content) {
        super(message);
        this.error = error;
        this.content = content;
    }

    public CustomException(String error, String message) {
        super(message);
        this.error = error;
    }
}
