package com.example.QuanLyPhongGym.app.phongtap.baiviet.command.update;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class UpdateBaiVietCommand {
    private String id;
    private String idNguoiDung;
    private String noiDung;
    private String hinhAnh;
    private Long ngayDangBai;
    private Long ngayCapNhat;
    private String tenBaiViet;
}
