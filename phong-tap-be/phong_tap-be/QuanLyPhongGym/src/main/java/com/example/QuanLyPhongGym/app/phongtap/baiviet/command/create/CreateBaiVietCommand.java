package com.example.QuanLyPhongGym.app.phongtap.baiviet.command.create;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateBaiVietCommand {
    private String id;
    private String idNguoiDung;
    private String hinhAnh;
    private String noiDung;
    private Long ngayDangBai;
    private String tenBaiViet;
}
