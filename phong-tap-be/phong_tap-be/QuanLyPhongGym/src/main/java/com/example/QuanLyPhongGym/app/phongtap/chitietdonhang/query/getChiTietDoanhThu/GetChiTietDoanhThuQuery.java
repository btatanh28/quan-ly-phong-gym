package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.getChiTietDoanhThu;

import java.math.BigDecimal;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetChiTietDoanhThuQuery {
    private String idGoiTap;
    private Integer soLuong;
    private BigDecimal gia;

}
