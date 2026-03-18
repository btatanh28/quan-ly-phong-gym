package com.example.QuanLyPhongGym.app.phongtap.thetap.query.getQrCodeKhachHang;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.ApiResponse;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap.TheTapGoiTapRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetQrCodeKhachHangQueryHandler {
    private final KhachHangRespository khachHangRespository;
    private final TheTapGoiTapRepository theTapGoiTapRepository;
    private final TheTapRespository theTapRespository;

    public ApiResponse<GetQrCodeResponse> handle(GetQrCodeKhachHangQuery request) {
        KhachHang khachHang = khachHangRespository.findFirstById(request.getId());

        if (khachHang == null) {
            throw new NotFoundException("Không tìm thấy khách hàng");
        }

        TheTap theTap = theTapRespository.findFirstByIdKhachHang(khachHang.getId());

        if (theTap == null) {
            throw new NotFoundException("Khách hàng chưa có thẻ tập");
        }

        GetQrCodeResponse response = new GetQrCodeResponse();
        response.setQrCode(theTap.getQrCode());

        List<TheTapGoiTap> list = theTapGoiTapRepository.findAllByIdTheTap(theTap.getId());

        int tongNgay = 0;
        for (TheTapGoiTap item : list) {
            tongNgay += item.getSoNgayConLai();
        }

        response.setSoNgayConLai(tongNgay);

        return new ApiResponse<>(response);
    }
}
