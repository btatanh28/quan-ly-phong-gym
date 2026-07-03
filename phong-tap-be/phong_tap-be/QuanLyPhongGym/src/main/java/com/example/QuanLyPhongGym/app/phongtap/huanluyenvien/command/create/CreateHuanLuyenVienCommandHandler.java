package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.create;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.huanluyenvien.HuanLuyenVien;
import com.example.QuanLyPhongGym.domain.repository.app.huanluyenvien.HuanLuyenVienRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateHuanLuyenVienCommandHandler {
    private final HuanLuyenVienRespository repository;

    public DataResponse handle(CreateHuanLuyenVienCommand request) {
        HuanLuyenVien huanLuyenVien = new HuanLuyenVien();

        huanLuyenVien.setId(request.getId());
        huanLuyenVien.setIdNguoiDung(request.getIdNguoiDung());
        huanLuyenVien.setIdKhachHang(request.getIdKhachHang());
        huanLuyenVien.setIdGoiTap(request.getIdGoiTap());
        huanLuyenVien.setSoDienThoai(request.getSoDienThoai());
        huanLuyenVien.setSoDienThoaiHLV(request.getSoDienThoaiHLV());
        huanLuyenVien.setEmailHLV(request.getEmailHLV());
        huanLuyenVien.setVaiTro(request.getVaiTro());

        repository.save(huanLuyenVien);

        return new DataResponse(request.getId());
    }
}
