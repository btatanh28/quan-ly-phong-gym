package com.example.QuanLyPhongGym.app.phongtap.danhgia.command.create;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateDanhGiaCommand {
    private String id;
    private String idNguoiDung;
    private String hinhAnh;
    private String binhLuan;
    private Long ngayDanhGia;
    private String idGoiTap;
    private String tenGoiTap;
    private Integer danhGia;
}
