package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.command.create;

import java.math.BigDecimal;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateChiTietDonHangCommand {
    private String id;
    private String idDonHang;
    private String idGoiTap;
    private Integer soLuong;
    private BigDecimal gia;
    private BigDecimal tongTien;
    private Integer giamGia;
}
