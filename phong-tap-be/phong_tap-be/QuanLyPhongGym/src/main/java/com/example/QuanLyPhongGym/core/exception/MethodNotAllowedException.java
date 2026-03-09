package com.example.QuanLyPhongGym.core.exception;

public class MethodNotAllowedException extends CustomException {

    public MethodNotAllowedException(String message) {
        super("405", message);
    }
}
