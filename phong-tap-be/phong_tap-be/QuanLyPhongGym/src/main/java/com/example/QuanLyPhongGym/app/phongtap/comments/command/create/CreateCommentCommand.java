package com.example.QuanLyPhongGym.app.phongtap.comments.command.create;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateCommentCommand {
    private String id;
    private String idNguoiDung;
    private String idKhachHang;
    private String idDanhGia;
    private String hinhAnh;
    private String noiDung;
    private Long ngayBinhLuan;
}
