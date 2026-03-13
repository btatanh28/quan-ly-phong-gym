package com.example.QuanLyPhongGym.app.phongtap.donhang.command.create;

import java.math.BigDecimal;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.command.create.CreateChiTietDonHangCommand;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.GenarateCode;
import com.example.QuanLyPhongGym.core.service.Generator;
import com.example.QuanLyPhongGym.domain.entity.app.chitietdonhang.ChiTietDonHang;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;
import com.example.QuanLyPhongGym.domain.enums.TrangThaiSanPhamEnums;
import com.example.QuanLyPhongGym.domain.repository.app.chitietdonhang.ChiTietDonHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap.TheTapGoiTapRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateDonHangCommandHandler {
    private final DonDangKyRespository respository;
    private final GoiTapRespository goiTapRespository;
    private final ChiTietDonHangRespository chiTietDonHangRespository;
    private final TheTapRespository theTapRespository;
    private final TheTapGoiTapRepository theTapGoiTapRepository;

    public DataResponse handle(CreateDonHangCommand request) {
        DonDangKy donDangKy = new DonDangKy();

        Long now = System.currentTimeMillis();

        donDangKy.setId(request.getId());
        donDangKy.setIdKhachHang(request.getIdKhachHang());
        donDangKy.setEmail(request.getEmail());
        // donDangKy.setIdGoiTap(request.getIdGoiTap());
        donDangKy.setSoDienThoai(request.getSoDienThoai());
        donDangKy.setIdNguoiDung(request.getIdNguoiDung());
        donDangKy.setNgayMua(now);
        donDangKy.setNgayThanhToan(now);
        donDangKy.setHinhThucThanhToan(request.getHinhThucThanhToan());
        donDangKy.setTongTien(request.getTongTien());

        if (request.getHinhThucThanhToan() == 1) {
            donDangKy.setTrangThaiSanPham(TrangThaiSanPhamEnums.DANGXULY.value);
        } else if (request.getHinhThucThanhToan() == 2) {
            donDangKy.setTrangThaiSanPham(TrangThaiSanPhamEnums.DATHANHTOAN.value);
        }

        respository.save(donDangKy);

        BigDecimal tongTien = BigDecimal.ZERO;

        for (CreateChiTietDonHangCommand ct : request.getChiTietDonHangs()) {

            GoiTap goiTap = goiTapRespository.findFirstById(ct.getIdGoiTap());

            if (goiTap == null) {
                throw new RuntimeException("San pham khong ton tai");
            }

            BigDecimal gia = goiTap.getGiaSauGiam() != null
                    ? goiTap.getGiaSauGiam()
                    : goiTap.getGia();

            BigDecimal thanhTien = gia.multiply(BigDecimal.valueOf(ct.getSoLuong()));

            tongTien = tongTien.add(thanhTien);

            ChiTietDonHang chiTietDonHang = new ChiTietDonHang();

            chiTietDonHang.setId(Generator.generate());
            chiTietDonHang.setIdDonHang(donDangKy.getId());
            chiTietDonHang.setIdGoiTap(ct.getIdGoiTap());
            chiTietDonHang.setSoLuong(ct.getSoLuong());
            chiTietDonHang.setGia(gia);
            chiTietDonHang.setGiamGia(ct.getGiamGia());
            chiTietDonHang.setTongTien(thanhTien);

            chiTietDonHangRespository.save(chiTietDonHang);
        }

        donDangKy.setTongTien(tongTien);
        respository.save(donDangKy);

        if (donDangKy.getTrangThaiSanPham() == TrangThaiSanPhamEnums.DATHANHTOAN.value) {

            Long nowTime = System.currentTimeMillis();

            // tìm thẻ tập của khách
            TheTap theTap = theTapRespository.findFirstByIdKhachHang(donDangKy.getIdKhachHang());

            // nếu chưa có thì tạo thẻ
            if (theTap == null) {
                theTap = new TheTap();
                theTap.setId(Generator.generate());
                theTap.setIdKhachHang(donDangKy.getIdKhachHang());
                theTap.setQrCode(GenarateCode.generate());
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

        return new DataResponse(donDangKy.getId());
    }
}
