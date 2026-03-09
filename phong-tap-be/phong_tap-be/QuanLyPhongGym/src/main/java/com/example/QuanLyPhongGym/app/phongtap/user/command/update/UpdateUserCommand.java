package com.example.QuanLyPhongGym.app.phongtap.user.command.update;



import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class UpdateUserCommand {
    private String id;
    private String tenNguoiDung;
    private String cccd;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private Integer vaiTro;
    private String hinhAnh;
    private Integer luong;
    private Long ngayVaoLam;
}
