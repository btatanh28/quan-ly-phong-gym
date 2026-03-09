package com.example.QuanLyPhongGym.core.model.response;

import com.example.QuanLyPhongGym.core.exception.CustomException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String error;

    private String message;

    private Object content;

    public ErrorResponse(CustomException e) {
        this.error = e.getError();
        this.message = e.getMessage();
        this.content = e.getContent();
    }
}
