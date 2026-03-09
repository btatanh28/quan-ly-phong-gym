package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.command.create;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.chitietdonhang.ChiTietDonHang;
import com.example.QuanLyPhongGym.domain.repository.app.chitietdonhang.ChiTietDonHangRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateChiTietDonHangCommandHandler {
    private final ChiTietDonHangRespository repository;

    public DataResponse handle(CreateChiTietDonHangCommand request) {
        ChiTietDonHang chiTietDonHang = new ChiTietDonHang();

        chiTietDonHang.setId(request.getId());
        chiTietDonHang.setIdDonHang(request.getIdDonHang());
        chiTietDonHang.setIdGoiTap(request.getIdGoiTap());
        chiTietDonHang.setSoLuong(request.getSoLuong());
        chiTietDonHang.setGia(request.getGia());
        chiTietDonHang.setTongTien(request.getTongTien());

        repository.save(chiTietDonHang);

        return new DataResponse(chiTietDonHang.getId());
    }
}
