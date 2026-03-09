package com.example.QuanLyPhongGym.app.phongtap.sanpham.command.update;

import java.math.BigDecimal;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class UpdateSanPhamCommand {
    private String id;
    private String tenGoiTap;
    private String hinhAnh;
    private BigDecimal gia;
    private String moTa;
    private Integer thoiHanNgay;
    private Integer soNgay;
    private Integer giamGia;
    private BigDecimal giaSauGiam;
}
