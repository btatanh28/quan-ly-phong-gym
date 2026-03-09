package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.get;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.mediator.IRequestHandler;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetKhachHangQueryHandler implements IRequestHandler<GetKhachHangQuery, GetKhachHangQueryDTO> {
    private final KhachHangRespository respository;

    public GetKhachHangQueryDTO handle(GetKhachHangQuery request) {
        KhachHang khachHang = respository.findFirstById(request.getId());

        if (khachHang.getId() == null) {
            throw new NotFoundException("Không tìm thấy dữ liệu");
        }

        GetKhachHangQueryDTO dto = new GetKhachHangQueryDTO();

        dto.setId(khachHang.getId());
        dto.setTenKhachHang(khachHang.getTenKhachHang());
        dto.setEmail(khachHang.getEmail());
        dto.setSoDienThoai(khachHang.getSoDienThoai());
        dto.setDiaChi(khachHang.getDiaChi());
        dto.setHinhAnh(khachHang.getHinhAnh());
        dto.setVaiTro(khachHang.getVaiTro());

        return dto;
    }
}
