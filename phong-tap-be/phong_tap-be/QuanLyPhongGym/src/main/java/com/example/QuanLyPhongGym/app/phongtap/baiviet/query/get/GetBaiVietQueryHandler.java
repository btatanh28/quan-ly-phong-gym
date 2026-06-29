package com.example.QuanLyPhongGym.app.phongtap.baiviet.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.domain.entity.app.baiviet.BaiViet;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.baiviet.BaiVietRespository;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetBaiVietQueryHandler {
    private final BaiVietRespository respository;

    private final UserRespository userRespository;

    public GetBaiVietQueryDTO handle(GetBaiVietQuery request) {
        GetBaiVietQueryDTO dto = new GetBaiVietQueryDTO();

        BaiViet baiViet = respository.findFirstById(request.getId());

        User user = userRespository.findFirstById(baiViet.getIdNguoiDung());

        dto.setId(baiViet.getId());
        dto.setIdNguoiDung(baiViet.getIdNguoiDung());
        dto.setTenNguoiDung(user.getTenNguoiDung());
        dto.setNoiDung(baiViet.getNoiDung());
        dto.setHinhAnh(baiViet.getHinhAnh());
        dto.setNgayDangBai(baiViet.getNgayDangBai());
        dto.setTenBaiViet(baiViet.getTenBaiViet());

        return dto;
    }
}
