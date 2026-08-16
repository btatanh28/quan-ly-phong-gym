package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getlist;

import java.math.BigDecimal;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.PageResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = PageResponse.class)
@Getter
@Setter

public class GetListSanPhamGymQuery {
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
