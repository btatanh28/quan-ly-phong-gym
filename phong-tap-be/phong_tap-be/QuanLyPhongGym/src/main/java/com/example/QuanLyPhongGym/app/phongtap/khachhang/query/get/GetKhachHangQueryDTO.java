package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.get;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetKhachHangQueryDTO {
    private String id;
    private String tenKhachHang;
    private String email;
    private String soDienThoai;
    private String diaChi;
    private Integer vaiTro;
    private String hinhAnh;
}
