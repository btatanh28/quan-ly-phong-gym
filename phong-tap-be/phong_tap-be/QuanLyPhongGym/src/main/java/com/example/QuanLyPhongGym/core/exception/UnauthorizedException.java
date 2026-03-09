package com.example.QuanLyPhongGym.core.exception;

public class UnauthorizedException extends CustomException {

    public UnauthorizedException(String message) {
        super("401", message);
    }
}
