package com.example.QuanLyPhongGym.core.exception;

public class BadRequestException extends CustomException {

    public BadRequestException(String message) {
        super("400", message);
    }

    public BadRequestException(String message, Object content) {
        super("400", message, content);
    }
}
