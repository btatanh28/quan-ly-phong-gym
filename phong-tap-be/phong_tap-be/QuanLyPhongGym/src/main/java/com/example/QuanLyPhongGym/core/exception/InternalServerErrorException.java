package com.example.QuanLyPhongGym.core.exception;

public class InternalServerErrorException extends CustomException {

    public InternalServerErrorException(String message) {
        super("500", message);
    }
}
