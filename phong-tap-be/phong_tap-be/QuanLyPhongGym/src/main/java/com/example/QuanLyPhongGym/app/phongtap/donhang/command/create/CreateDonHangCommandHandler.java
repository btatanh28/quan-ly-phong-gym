package com.example.QuanLyPhongGym.app.phongtap.donhang.command.create;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

        private final DonDangKyRespository donDangKyRepository;

        private final GoiTapRespository goiTapRespository;

        private final ChiTietDonHangRespository chiTietDonHangRespository;

        private final TheTapRespository theTapRespository;

        private final TheTapGoiTapRepository theTapGoiTapRepository;

        public DataResponse handle(CreateDonHangCommand request) {

                Long now = System.currentTimeMillis();

                DonDangKy donDangKy = new DonDangKy();

                /*
                 * ID đơn hàng chính là orderId gửi MoMo
                 */
                donDangKy.setId(
                                request.getId());

                donDangKy.setIdKhachHang(
                                request.getIdKhachHang());

                donDangKy.setEmail(
                                request.getEmail());

                donDangKy.setSoDienThoai(
                                request.getSoDienThoai());

                donDangKy.setIdNguoiDung(
                                request.getIdNguoiDung());

                donDangKy.setNgayMua(
                                now);

                donDangKy.setHinhThucThanhToan(
                                request.getHinhThucThanhToan());

                /*
                 * Xử lý trạng thái thanh toán
                 */
                if (request.getHinhThucThanhToan() == 1) {

                        // MoMo
                        donDangKy.setNgayThanhToan(null);

                        donDangKy.setTrangThaiSanPham(
                                        TrangThaiSanPhamEnums.CHOTHANHTOAN.value);

                } else {

                        // Tiền mặt
                        donDangKy.setNgayThanhToan(now);

                        donDangKy.setTrangThaiSanPham(
                                        TrangThaiSanPhamEnums.CHOTHANHTOAN.value);

                }

                donDangKyRepository.save(
                                donDangKy);

                BigDecimal tongTien = BigDecimal.ZERO;

                /*
                 * Lưu chi tiết đơn hàng
                 */
                for (CreateChiTietDonHangCommand ct : request.getChiTietDonHangs()) {

                        GoiTap goiTap = goiTapRespository
                                        .findFirstById(
                                                        ct.getIdGoiTap());

                        if (goiTap == null) {

                                throw new RuntimeException(
                                                "Gói tập không tồn tại");

                        }

                        BigDecimal gia = goiTap.getGiaSauGiam() != null
                                        ? goiTap.getGiaSauGiam()
                                        : goiTap.getGia();

                        BigDecimal thanhTien = gia.multiply(
                                        BigDecimal.valueOf(
                                                        ct.getSoLuong()));

                        tongTien = tongTien.add(
                                        thanhTien);

                        ChiTietDonHang chiTiet = new ChiTietDonHang();

                        chiTiet.setId(
                                        Generator.generate());

                        chiTiet.setIdDonHang(
                                        donDangKy.getId());

                        chiTiet.setIdGoiTap(
                                        ct.getIdGoiTap());

                        chiTiet.setSoLuong(
                                        ct.getSoLuong());

                        chiTiet.setGia(
                                        gia);

                        chiTiet.setGiamGia(
                                        ct.getGiamGia());

                        chiTiet.setTongTien(
                                        thanhTien);

                        chiTietDonHangRespository.save(
                                        chiTiet);

                }

                donDangKy.setTongTien(
                                tongTien);

                donDangKyRepository.save(
                                donDangKy);

                /*
                 * Nếu thanh toán tiền mặt
                 * thì tạo thẻ ngay
                 */
                if (Objects.equals(
                                donDangKy.getTrangThaiSanPham(),
                                TrangThaiSanPhamEnums.DATHANHTOAN.value)) {

                        taoTheTap(
                                        donDangKy,
                                        request.getChiTietDonHangs());

                }

                return new DataResponse(
                                donDangKy.getId());

        }

        /*
         * Tạo thẻ tập sau khi thanh toán thành công
         * Dùng cho:
         * - Tiền mặt
         * - MoMo callback
         */
        public void taoTheTap(
                        DonDangKy donDangKy,
                        List<CreateChiTietDonHangCommand> chiTietHangs) {

                Long now = System.currentTimeMillis();

                TheTap theTap = theTapRespository
                                .findFirstByIdKhachHang(
                                                donDangKy.getIdKhachHang());

                if (theTap == null) {

                        theTap = new TheTap();

                        theTap.setId(
                                        Generator.generate());

                        theTap.setIdKhachHang(
                                        donDangKy.getIdKhachHang());

                        theTap.setQrCode(
                                        GenarateCode.generate());

                        theTap.setTrangThai(1);

                        theTap.setNgayTao(
                                        now);

                        theTapRespository.save(
                                        theTap);

                }

                for (CreateChiTietDonHangCommand ct : chiTietHangs) {

                        GoiTap goiTap = goiTapRespository
                                        .findFirstById(
                                                        ct.getIdGoiTap());

                        TheTapGoiTap theTapGoiTap = new TheTapGoiTap();

                        theTapGoiTap.setId(
                                        Generator.generate());

                        theTapGoiTap.setIdTheTap(
                                        theTap.getId());

                        theTapGoiTap.setIdGoiTap(
                                        ct.getIdGoiTap());

                        theTapGoiTap.setNgayBatDau(
                                        now);

                        theTapGoiTap.setNgayKetThuc(
                                        now
                                                        +
                                                        (goiTap.getSoNgay()
                                                                        *
                                                                        24
                                                                        *
                                                                        60
                                                                        *
                                                                        60
                                                                        *
                                                                        1000L));

                        theTapGoiTap.setSoNgayConLai(
                                        goiTap.getSoNgay());

                        theTapGoiTap.setTrangThai(1);

                        theTapGoiTap.setNgayTao(
                                        now);

                        theTapGoiTapRepository.save(
                                        theTapGoiTap);

                }

        }
}