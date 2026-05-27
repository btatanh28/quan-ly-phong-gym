package com.example.QuanLyPhongGym.app.phongtap.donhang.query.exportDoanhThu;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportDoanhThuQueryDTO {
    private int stt;
    private String ngay;
    private Integer thang;
    private Integer nam;
    private Double tongTien;
    private Double tongTienDoanhThu;
}
