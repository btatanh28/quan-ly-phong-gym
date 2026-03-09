package com.example.QuanLyPhongGym.app.phongtap.sanpham.command.create;

import java.math.BigDecimal;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateSanPhamCommand {
    private String id;
    private String idGoiTap;
    private String tenGoiTap;
    private Integer soTonKho;
    private String hinhAnh;
    private BigDecimal gia;
    private String moTa;
    private Integer thoiHanNgay;
    private Integer giamGia;
    private Integer soNgay;
}
