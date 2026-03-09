package com.example.QuanLyPhongGym.app.phongtap.user.query.getlist;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = ListResponse.class)
@Getter
@Setter

public class GetListUserQuery {
    private String id;
    private String tenNguoiDung;
    private String cccd;
    private String email;
    private String matKhau;
    private String soDienThoai;
    private String diaChi;
    private Integer vaiTro;
    private String hinhAnh;
    private Integer luong;
    private Long ngayVaoLam;
    private Long ngayVaoLamTu;
    private Long ngayVaoLamDen;
}
