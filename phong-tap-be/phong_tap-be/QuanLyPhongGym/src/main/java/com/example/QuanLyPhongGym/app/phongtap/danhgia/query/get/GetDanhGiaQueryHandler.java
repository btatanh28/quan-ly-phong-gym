package com.example.QuanLyPhongGym.app.phongtap.danhgia.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.domain.repository.app.danhgia.DanhGiaRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetDanhGiaQueryHandler {
    private final DanhGiaRespository respository;

    public GetDanhGiaQueryDTO handle(GetDanhGiaQuery request) {
        GetDanhGiaQueryDTO dto = new GetDanhGiaQueryDTO();

        var baiViet = respository.findFirstById(request.getId());

        dto.setId(baiViet.getId());
        dto.setIdKhachHang(baiViet.getIdKhachHang());
        dto.setBinhLuan(baiViet.getBinhLuan());
        dto.setHinhAnh(baiViet.getHinhAnh());
        dto.setDanhGia(baiViet.getDanhGia());
        dto.setIdGoiTap(baiViet.getIdGoiTap());

        return dto;
    }
}
