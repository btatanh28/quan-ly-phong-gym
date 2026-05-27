package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.exportKhachHang;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportKhachHangQueryResponse {
    private String ngay;
    private String thang;
    private String nam;
    private Long tongDoanhThu;
    private List<ExportKhachHangQueryDTO> items;
}
