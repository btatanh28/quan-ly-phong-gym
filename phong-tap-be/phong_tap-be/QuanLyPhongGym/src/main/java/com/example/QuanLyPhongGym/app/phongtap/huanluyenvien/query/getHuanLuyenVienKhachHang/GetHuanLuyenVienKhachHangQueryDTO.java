package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.getHuanLuyenVienKhachHang;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetHuanLuyenVienKhachHangQueryDTO {
    private String idKhachHang;
    private String tenNguoiDung;
    private String idNguoiDung;
    private String idGoiTap;
    private String soDienThoaiHLV;
    private String emailHLV;
    private String hinhAnh;
    private String ghiChu;
    private boolean daDangKy;
}
