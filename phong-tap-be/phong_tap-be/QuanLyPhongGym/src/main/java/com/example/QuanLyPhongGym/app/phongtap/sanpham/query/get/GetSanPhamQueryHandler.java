package com.example.QuanLyPhongGym.app.phongtap.sanpham.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetSanPhamQueryHandler {
    private final GoiTapRespository respository;

    public GetSanPhamQueryDTO handle(GetSanPhamQuery request) {
        GoiTap goiTap = respository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không tìm thấy sản phẩm");
        }

        GetSanPhamQueryDTO dto = new GetSanPhamQueryDTO();

        dto.setId(goiTap.getId());
        dto.setTenGoiTap(goiTap.getTenGoiTap());
        dto.setThoiHanNgay(goiTap.getThoiHanNgay());
        dto.setGiamGia(goiTap.getGiamGia());
        dto.setHinhAnh(goiTap.getHinhAnh());
        dto.setGia(goiTap.getGia());
        dto.setMoTa(goiTap.getMoTa());

        return dto;
    }
}
