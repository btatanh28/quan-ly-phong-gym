package com.example.QuanLyPhongGym.app.phongtap.comments.query.getlist;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListCommentQuery {
    private String id;
    private String idNguoiDung;
    private String idKhachHang;
    private String idDanhGia;
    private String hinhAnh;
    private String noiDung;
    private Long ngayBinhLuan;
    private String tenKhachHang;
    private String tenNguoiDung;
    private Integer page;
    private Integer size;
}
