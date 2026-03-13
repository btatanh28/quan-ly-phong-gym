package com.example.QuanLyPhongGym.app.phongtap.checkin.command;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.ApiResponse;
import com.example.QuanLyPhongGym.core.service.Generator;
import com.example.QuanLyPhongGym.domain.entity.app.checkin.CheckIn;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.repository.app.checkin.CheckInRepository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CheckInCommandHandler {
    private final TheTapRespository theTapRespository;
    private final CheckInRepository checkInRepository;

    public ApiResponse<String> handle(CheckInCommand request) {

        TheTap theTap = theTapRespository.findFirstByQrCode(request.getQrCode());

        if (theTap == null) {
            throw new RuntimeException("QR không hợp lệ");
        }

        long now = System.currentTimeMillis();

        long start = now - (now % 86400000);
        long end = start + 86400000;

        CheckIn exist = checkInRepository
                .findFirstByIdTheTapAndThoiGianBetween(
                        theTap.getId(),
                        start,
                        end);

        if (exist != null) {
            throw new RuntimeException("Hôm nay đã check-in");
        }

        CheckIn checkIn = new CheckIn();

        checkIn.setId(Generator.generate());
        checkIn.setIdTheTap(theTap.getId());
        checkIn.setIdKhachHang(theTap.getIdKhachHang());
        checkIn.setThoiGian(now);
        checkIn.setThietBi(request.getThietBi());
        checkIn.setTrangThai(1);

        checkInRepository.save(checkIn);

        return new ApiResponse<>("Check-in thành công");
    }
}
