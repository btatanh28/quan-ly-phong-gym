package com.example.QuanLyPhongGym.app.login.command;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class ChangePasswordCommand {
    private String email;
    private String matKhau;
    private String xacNhanMatKhau;
}
