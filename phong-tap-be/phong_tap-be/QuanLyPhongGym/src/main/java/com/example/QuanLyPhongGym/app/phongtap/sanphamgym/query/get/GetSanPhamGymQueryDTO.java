package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.get;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetSanPhamGymQueryDTO {
    private String id;
    private String tenSanPham;
    private Integer soTonKho;
    private Integer giamGia;
    private String hinhAnh;
    private BigDecimal gia;
    private String moTa;
}
