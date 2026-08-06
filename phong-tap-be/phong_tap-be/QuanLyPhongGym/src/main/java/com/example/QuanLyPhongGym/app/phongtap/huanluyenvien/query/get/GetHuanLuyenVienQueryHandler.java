package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.CustomException;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.huanluyenvien.HuanLuyenVien;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.huanluyenvien.HuanLuyenVienRespository;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetHuanLuyenVienQueryHandler {
    private final HuanLuyenVienRespository repository;
    private final GoiTapRespository goiTapRespository;
    private final KhachHangRespository khachHangRespository;
    private final UserRespository userRepository;

    public GetHuanLuyenVienQueryDTO handle(GetHuanLuyenVienQuery request) {
        HuanLuyenVien huanLuyenVien = repository.findFirstById(request.getId());
        GoiTap goiTap = goiTapRespository.findFirstById(huanLuyenVien.getIdGoiTap());
        KhachHang khachHang = khachHangRespository.findFirstById(huanLuyenVien.getIdKhachHang());
        User user = userRepository.findFirstById(huanLuyenVien.getIdNguoiDung());

        if (huanLuyenVien == null) {
            throw new CustomException("404", "Không tìm thấy dữ liệu");
        }

        GetHuanLuyenVienQueryDTO dto = new GetHuanLuyenVienQueryDTO();
        dto.setId(huanLuyenVien.getId());
        dto.setIdGoiTap(huanLuyenVien.getIdGoiTap());
        dto.setIdKhachHang(huanLuyenVien.getIdKhachHang());
        dto.setIdNguoiDung(huanLuyenVien.getIdNguoiDung());
        dto.setTenNguoiDung(user.getTenNguoiDung());
        dto.setTenKhachHang(khachHang.getTenKhachHang());
        dto.setTenGoiTap(goiTap.getTenGoiTap());
        dto.setSoDienThoai(huanLuyenVien.getSoDienThoai());
        dto.setSoDienThoaiHLV(huanLuyenVien.getSoDienThoaiHLV());
        dto.setSoDienThoai(huanLuyenVien.getSoDienThoai());
        dto.setVaiTro(huanLuyenVien.getVaiTro());
        dto.setEmailHLV(huanLuyenVien.getEmailHLV());
        dto.setGhiChu(huanLuyenVien.getGhiChu());
        return dto;
    }

}
