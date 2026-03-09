package com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getlist;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListSanPhamQueryDTO {
    private String id;
    private String tenGoiTap;
    private String hinhAnh;
    private BigDecimal gia;
    private String moTa;
    private Integer thoiHanNgay;
    private Integer giamGia;
    private BigDecimal giaSauGiam;
    private Integer soNgay;
}
