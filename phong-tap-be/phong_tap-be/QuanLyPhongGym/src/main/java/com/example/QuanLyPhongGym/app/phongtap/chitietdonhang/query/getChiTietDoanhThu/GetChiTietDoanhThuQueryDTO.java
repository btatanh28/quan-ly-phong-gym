package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.getChiTietDoanhThu;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetChiTietDoanhThuQueryDTO {
    private String idGoiTap;
    private Integer soLuong;
    private BigDecimal gia;
    private String tenGoiTap;
    private BigDecimal tongTien;
}
