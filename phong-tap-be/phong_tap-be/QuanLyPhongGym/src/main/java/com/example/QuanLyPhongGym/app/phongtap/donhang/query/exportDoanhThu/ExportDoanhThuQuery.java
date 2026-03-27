package com.example.QuanLyPhongGym.app.phongtap.donhang.query.exportDoanhThu;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;

import com.example.QuanLyPhongGym.core.annotation.Response;

@Response(target = InputStreamResource.class)
@Getter
@Setter
public class ExportDoanhThuQuery {
    private Integer month;
    private Integer year;
}
