package com.example.QuanLyPhongGym.app.phongtap.lienhe.command.create;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.lienhe.LienHe;
import com.example.QuanLyPhongGym.domain.repository.app.lienhe.LienHeRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateLienHeCommandHandler {
    private final LienHeRepository lienHeRepository;

    public DataResponse handle(CreateLienHeCommand request) {
        LienHe lienHe = new LienHe();

        Long now = System.currentTimeMillis();

        lienHe.setId(request.getId());
        lienHe.setIdKhachHang(request.getIdKhachHang());
        lienHe.setTenKhachHang(request.getTenKhachHang());
        lienHe.setEmail(request.getEmail());
        lienHe.setSoDienThoai(request.getSoDienThoai());
        lienHe.setNoiDung(request.getNoiDung());
        lienHe.setNgayGui(now);

        lienHeRepository.save(lienHe);

        return new DataResponse(lienHe.getId());
    }
}
