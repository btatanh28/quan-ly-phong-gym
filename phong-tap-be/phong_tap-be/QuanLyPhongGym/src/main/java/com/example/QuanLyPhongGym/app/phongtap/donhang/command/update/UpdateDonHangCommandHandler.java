package com.example.QuanLyPhongGym.app.phongtap.donhang.command.update;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.ThanhToanService;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.enums.HinhThucThanhToanEnums;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateDonHangCommandHandler {
    private final DonDangKyRespository respository;

    private final ThanhToanService thanhToanService;

    public DataResponse handle(UpdateDonHangCommand request) {
        DonDangKy donDangKy = respository.findFirstById(request.getId());

        if (donDangKy.getId() == null) {
            throw new NotFoundException("Không tìm thấy dữ liệu");
        }

        Long now = System.currentTimeMillis();

        donDangKy.setIdNguoiDung(request.getIdNguoiDung());
        donDangKy.setNgayThanhToan(now);
        donDangKy.setNgayCapNhat(now);

        if (request.getTrangThaiSanPham() == 2) {
            thanhToanService.xuLyThanhToanThanhCong(donDangKy.getId());
        } else if (request.getTrangThaiSanPham() == 3) {
            donDangKy.setTrangThaiSanPham(3);
            respository.save(donDangKy);
        } else if (request.getTrangThaiSanPham() == 4) {
            donDangKy.setTrangThaiSanPham(4);
            respository.save(donDangKy);
        } else if (request.getTrangThaiSanPham() == 1) {
            donDangKy.setTrangThaiSanPham(1);
            respository.save(donDangKy);
        }

        return new DataResponse(donDangKy.getId());
    }
}
