package com.example.QuanLyPhongGym.app.phongtap.user.query.get;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetUserQueryDTO {
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
