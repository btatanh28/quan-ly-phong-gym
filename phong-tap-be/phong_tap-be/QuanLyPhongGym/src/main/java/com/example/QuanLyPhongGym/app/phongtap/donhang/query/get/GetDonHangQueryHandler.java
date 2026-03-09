package com.example.QuanLyPhongGym.app.phongtap.donhang.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetDonHangQueryHandler {
    private final DonDangKyRespository respository;

    public GetDonHangQueryDTO handle(GetDonHangQuery request) {
        DonDangKy donHang = respository.findFirstById(request.getId());

        if (donHang.getId() == null) {
            throw new NotFoundException("Không tìm thấy dữ liệu");
        }

        GetDonHangQueryDTO dto = new GetDonHangQueryDTO();

        dto.setId(donHang.getId());
        dto.setTrangThaiSanPham(donHang.getTrangThaiSanPham());

        return dto;
    }
}
