package com.example.QuanLyPhongGym.app.phongtap.donhang.command.update;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.command.create.CreateChiTietDonHangCommand;
import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.GenarateCode;
import com.example.QuanLyPhongGym.core.service.Generator;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;
import com.example.QuanLyPhongGym.domain.enums.TrangThaiSanPhamEnums;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap.TheTapGoiTapRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateDonHangCommandHandler {
    private final DonDangKyRespository respository;
    private final GoiTapRespository goiTapRespository;
    private final TheTapRespository theTapRespository;
    private final TheTapGoiTapRepository theTapGoiTapRepository;

    public DataResponse handle(UpdateDonHangCommand request) {
        DonDangKy donDangKy = respository.findFirstById(request.getId());

        if (donDangKy.getId() == null) {
            throw new NotFoundException("Không tìm thấy dữ liệu");
        }

        Long now = System.currentTimeMillis();

        donDangKy.setTrangThaiSanPham(request.getTrangThaiSanPham());
        donDangKy.setIdNguoiDung(request.getIdNguoiDung());
        donDangKy.setNgayCapNhat(now);

        if (donDangKy.getTrangThaiSanPham() == TrangThaiSanPhamEnums.DATHANHTOAN.value) {

            Long nowTime = System.currentTimeMillis();

            // tìm thẻ tập của khách
            TheTap theTap = theTapRespository.findFirstByIdKhachHang(donDangKy.getIdKhachHang());

            // nếu chưa có thì tạo thẻ
            if (theTap == null) {
                theTap = new TheTap();
                theTap.setId(GenarateCode.generate());
                theTap.setIdKhachHang(donDangKy.getIdKhachHang());
                theTap.setQrCode(System.currentTimeMillis());
                theTap.setTrangThai(1);
                theTap.setNgayTao(nowTime);

                theTapRespository.save(theTap);
            }

            // thêm các gói tập vào bảng THE_TAP_GOI_TAP
            for (CreateChiTietDonHangCommand ct : request.getChiTietDonHangs()) {

                GoiTap goiTap = goiTapRespository.findFirstById(ct.getIdGoiTap());

                TheTapGoiTap theTapGoiTap = new TheTapGoiTap();

                theTapGoiTap.setId(Generator.generate());
                theTapGoiTap.setIdTheTap(theTap.getId());
                theTapGoiTap.setIdGoiTap(ct.getIdGoiTap());

                theTapGoiTap.setNgayBatDau(nowTime);

                Long ngayKetThuc = nowTime + (goiTap.getSoNgay() * 24 * 60 * 60 * 1000L);

                theTapGoiTap.setNgayKetThuc(ngayKetThuc);
                theTapGoiTap.setSoNgayConLai(goiTap.getSoNgay());
                theTapGoiTap.setTrangThai(1);
                theTapGoiTap.setNgayTao(nowTime);

                theTapGoiTapRepository.save(theTapGoiTap);
            }
        }

        respository.save(donDangKy);

        return new DataResponse(donDangKy.getId());
    }
}
