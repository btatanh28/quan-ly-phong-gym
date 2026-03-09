package com.example.QuanLyPhongGym.app.phongtap.donhang.command.update;

import java.util.List;

import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.command.create.CreateChiTietDonHangCommand;
import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class UpdateDonHangCommand {
    private String id;
    private Integer trangThaiSanPham;
    private String idNguoiDung;
    private String ngayCapNhat;
    private List<CreateChiTietDonHangCommand> chiTietDonHangs;
}
