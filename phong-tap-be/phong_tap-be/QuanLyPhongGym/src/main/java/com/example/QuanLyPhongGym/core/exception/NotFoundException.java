package com.example.QuanLyPhongGym.core.exception;

public class NotFoundException extends CustomException {

    public NotFoundException(String error, String message) {
        super(error, message);
    }

    public NotFoundException(String message) {
        super("404", message);
    }
}
