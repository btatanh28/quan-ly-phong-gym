package com.example.QuanLyPhongGym.app.phongtap.thetap.command.create;

import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateTheTapCommand {
    private String id;
    private String idKhachHang;
    private String idGoiTap;
    private String ngayBatDau;
    private String ngayKetThuc;
    private Integer soNgayConLai;
    private String qrCode;
    private Integer trangThai;
    private Long ngayTao;
}
