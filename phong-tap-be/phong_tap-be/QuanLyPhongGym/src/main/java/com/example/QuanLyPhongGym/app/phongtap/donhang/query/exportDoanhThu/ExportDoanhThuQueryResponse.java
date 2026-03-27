package com.example.QuanLyPhongGym.app.phongtap.donhang.query.exportDoanhThu;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExportDoanhThuQueryResponse {
    private String nguoiCapNhat;
    private String tinh;
    private String xa;
    private Long tinhId;
    private Long xaId;
    private List<ExportDoanhThuQueryDTO> items;
}
