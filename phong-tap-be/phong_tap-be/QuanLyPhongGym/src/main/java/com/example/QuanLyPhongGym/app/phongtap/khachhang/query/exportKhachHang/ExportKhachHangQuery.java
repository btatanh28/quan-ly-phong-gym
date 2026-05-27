package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.exportKhachHang;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;

import com.example.QuanLyPhongGym.core.annotation.Response;

@Response(target = InputStreamResource.class)
@Getter
@Setter
public class ExportKhachHangQuery {
    private int stt;
    private String tenKhachHang;
    private String email;
    private String soDienThoai;
    private String diaChi;
}
