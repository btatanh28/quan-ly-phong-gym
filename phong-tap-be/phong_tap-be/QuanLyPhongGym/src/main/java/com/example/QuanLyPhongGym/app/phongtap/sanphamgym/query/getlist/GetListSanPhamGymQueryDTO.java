package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getlist;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetListSanPhamGymQueryDTO {
    private String id;
    private String tenSanPham;
    private String hinhAnh;
    private BigDecimal gia;
    private String moTa;
    private Integer soTonKho;
    private Integer giamGia;
    private BigDecimal giaSauGiam;
    private Integer page;
    private Integer size;
}
