package com.example.QuanLyPhongGym.app.phongtap.user.command.create;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateUserCommand {
    private String id;
    private String tenNguoiDung;
    private String cccd;
    private String email;
    private String matKhau;
    private String soDienThoai;
    private String diaChi;
    private Integer vaiTro;
    private String hinhAnh;
    private Integer luong;
    private Long ngayVaoLam;
}
