package com.example.QuanLyPhongGym.app.phongtap.baiviet.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.domain.repository.app.baiviet.BaiVietRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetBaiVietQueryHandler {
    private final BaiVietRespository respository;

    public GetBaiVietQueryDTO handle(GetBaiVietQuery request) {
        GetBaiVietQueryDTO dto = new GetBaiVietQueryDTO();

        var baiViet = respository.findFirstById(request.getId());

        dto.setId(baiViet.getId());
        dto.setIdNguoiDung(baiViet.getIdNguoiDung());
        dto.setNoiDung(baiViet.getNoiDung());
        dto.setHinhAnh(baiViet.getHinhAnh());
        dto.setNgayDangBai(baiViet.getNgayDangBai());
        dto.setTenBaiViet(baiViet.getTenBaiViet());

        return dto;
    }
}
