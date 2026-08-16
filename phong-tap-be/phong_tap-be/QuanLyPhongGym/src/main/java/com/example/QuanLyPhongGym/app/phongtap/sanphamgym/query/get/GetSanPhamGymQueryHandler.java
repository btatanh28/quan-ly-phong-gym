package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.domain.entity.app.sanpham.SanPham;
import com.example.QuanLyPhongGym.domain.repository.app.sanpham.SanPhamRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetSanPhamGymQueryHandler {
    private final SanPhamRepository respository;

    public GetSanPhamGymQueryDTO handle(GetSanPhamGymQuery request) {
        SanPham sanPham = respository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không tìm thấy sản phẩm");
        }

        GetSanPhamGymQueryDTO dto = new GetSanPhamGymQueryDTO();

        dto.setId(sanPham.getId());
        dto.setTenSanPham(sanPham.getTenSanPham());
        dto.setSoTonKho(sanPham.getSoTonKho());
        dto.setGiamGia(sanPham.getGiamGia());
        dto.setHinhAnh(sanPham.getHinhAnh());
        dto.setGia(sanPham.getGia());
        dto.setMoTa(sanPham.getMoTa());

        return dto;
    }
}
