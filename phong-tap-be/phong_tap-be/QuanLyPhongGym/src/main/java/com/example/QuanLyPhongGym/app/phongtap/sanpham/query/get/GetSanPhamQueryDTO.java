package com.example.QuanLyPhongGym.app.phongtap.sanpham.query.get;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetSanPhamQueryDTO {
    private String id;
    private String tenGoiTap;
    private Integer thoiHanNgay;
    private Integer giamGia;
    private String hinhAnh;
    private BigDecimal gia;
    private String moTa;
}
