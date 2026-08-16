package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.create;

import java.math.BigDecimal;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateSanPhamGymCommand {
    private String id;
    private String tenSanPham;
    private String hinhAnh;
    private BigDecimal gia;
    private Integer soTonKho;
    private String moTa;
    private Integer giamGia;
    private BigDecimal giaSauGiam;
}
