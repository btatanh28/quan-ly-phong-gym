package com.example.QuanLyPhongGym.app.phongtap.donhang.query.getDoanhThu;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetDoanhThuQueryDTO {
    private Integer thang;
    private Integer ngay;
    private Integer nam;
    private BigDecimal tongTienDoanhThu;
}
