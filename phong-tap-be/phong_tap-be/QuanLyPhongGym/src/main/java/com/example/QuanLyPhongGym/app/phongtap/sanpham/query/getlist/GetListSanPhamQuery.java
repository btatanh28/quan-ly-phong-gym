package com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getlist;

import java.math.BigDecimal;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.PageResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = PageResponse.class)
@Getter
@Setter

public class GetListSanPhamQuery {
    private String id;
    private String tenGoiTap;
    private String hinhAnh;
    private BigDecimal gia;
    private String moTa;
    private Integer thoiHanNgay;
    private Integer giamGia;
    private BigDecimal giaSauGiam;
    private Integer soNgay;
    private Integer page;
    private Integer size;
}
