package com.example.QuanLyPhongGym.app.login.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String token;
    private Integer role;
    private String id;
    private String name;
    private String soDienThoai;
    private String diaChi;
}
