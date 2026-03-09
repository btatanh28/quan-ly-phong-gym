package com.example.QuanLyPhongGym.app.phongtap.danhgia.command.update;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class UpdateDanhGiaCommand {
    private String id;
    private String hinhAnh;
    private String binhLuan;
    private String idGoiTap;
    private String tenGoiTap;
    private Integer danhGia;
}
