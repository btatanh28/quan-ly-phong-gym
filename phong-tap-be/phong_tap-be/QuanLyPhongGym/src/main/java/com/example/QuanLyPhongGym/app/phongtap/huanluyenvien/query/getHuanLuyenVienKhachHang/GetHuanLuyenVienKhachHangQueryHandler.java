package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.getHuanLuyenVienKhachHang;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.domain.entity.app.huanluyenvien.HuanLuyenVien;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.entity.app.user.User;
import com.example.QuanLyPhongGym.domain.repository.app.huanluyenvien.HuanLuyenVienRespository;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.user.UserRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetHuanLuyenVienKhachHangQueryHandler {
    private final KhachHangRespository repository;

    private final HuanLuyenVienRespository huanLuyenVienRespository;

    private final UserRespository userRepository;

    public GetHuanLuyenVienKhachHangQueryDTO handle(GetHuanLuyenVienKhachHangQuery request) {
        KhachHang khachHang = repository.findFirstById(request.getId());

        if (khachHang == null) {
            throw new RuntimeException("Không tìm thấy khách hàng");
        }

        HuanLuyenVien huanLuyenVien = huanLuyenVienRespository.findFirstByIdKhachHang(khachHang.getId());
        
        if (huanLuyenVien == null) {
            GetHuanLuyenVienKhachHangQueryDTO dto = new GetHuanLuyenVienKhachHangQueryDTO();
            dto.setDaDangKy(false);
            return dto;
        }

        User user = userRepository.findFirstById(huanLuyenVien.getIdNguoiDung());

        if (user == null) {
            throw new RuntimeException("Không tìm thấy tài khoản huấn luyện viên");
        }


        GetHuanLuyenVienKhachHangQueryDTO dto = new GetHuanLuyenVienKhachHangQueryDTO();
        dto.setDaDangKy(true);
        dto.setIdNguoiDung(user.getId());
        dto.setTenNguoiDung(user.getTenNguoiDung());
        dto.setSoDienThoaiHLV(huanLuyenVien.getSoDienThoaiHLV());
        dto.setEmailHLV(huanLuyenVien.getEmailHLV());
        dto.setGhiChu(huanLuyenVien.getGhiChu());
        dto.setHinhAnh(huanLuyenVien.getHinhAnh());

        return dto;
    }
}
