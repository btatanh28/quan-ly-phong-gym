package com.example.QuanLyPhongGym.app.phongtap.donhang.command.create;

import java.math.BigDecimal;
import java.util.List;

import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.command.create.CreateChiTietDonHangCommand;
import com.example.QuanLyPhongGym.core.annotation.Response;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import lombok.Getter;
import lombok.Setter;

@Response(target = DataResponse.class)
@Getter
@Setter

public class CreateDonHangCommand {
    private String id;
    private String idKhachHang;
    private String idNguoiDung;
    private Long ngayMua;
    private Long ngayThanhToan;
    private Integer hinhThucThanhToan;
    private Integer trangThaiSanPham;
    private BigDecimal tongTien;
    private String soDienThoai;
    private String email;
    private Integer soNgay;
    private List<CreateChiTietDonHangCommand> chiTietDonHangs;
}
