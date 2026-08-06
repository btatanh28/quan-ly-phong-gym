package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.create;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateHuanLuyenVienCommand {
    private String id;
    private String idNguoiDung;
    private String idKhachHang;
    private String idGoiTap;
    private String soDienThoai;
    private String soDienThoaiHLV;
    private String emailHLV;
    private Integer vaiTro;
    private String hinhAnh;
    private String ghiChu;
}
