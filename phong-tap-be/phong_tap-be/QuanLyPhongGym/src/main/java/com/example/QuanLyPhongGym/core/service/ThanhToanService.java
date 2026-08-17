package com.example.QuanLyPhongGym.core.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.QuanLyPhongGym.domain.entity.app.chitietdonhang.ChiTietDonHang;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.sanpham.SanPham;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;
import com.example.QuanLyPhongGym.domain.enums.TrangThaiSanPhamEnums;
import com.example.QuanLyPhongGym.domain.repository.app.chitietdonhang.ChiTietDonHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.sanpham.SanPhamRepository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap.TheTapGoiTapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThanhToanService {
    private final DonDangKyRespository donDangKyRespository;
    private final ChiTietDonHangRespository chiTietDonHangRespository;
    private final SanPhamRepository sanPhamRepository;
    private final TheTapRespository theTapRespository;
    private final TheTapGoiTapRepository theTapGoiTapRepository;
    private final GoiTapRespository goiTapRespository;

    @Transactional
    public void xuLyThanhToanThanhCong(String orderId) {

        DonDangKy donDangKy = donDangKyRespository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng: " + orderId));

        // Chống IPN gọi nhiều lần
        if (TrangThaiSanPhamEnums.DATHANHTOAN.value
                .equals(donDangKy.getTrangThaiSanPham())) {

            return;
        }

        List<ChiTietDonHang> chiTietDonHangs = chiTietDonHangRespository.findByIdDonHang(orderId);

        // 2. Trừ tồn kho
        truTonKho(chiTietDonHangs);

        // 3. Cập nhật trạng thái thanh toán
        donDangKy.setTrangThaiSanPham(
                TrangThaiSanPhamEnums.DATHANHTOAN.value);

        taoTheTap(donDangKy, chiTietDonHangs);

        donDangKy.setNgayThanhToan(
                System.currentTimeMillis());

        donDangKyRespository.save(donDangKy);

        // 4. Tạo thẻ tập nếu đơn có gói tập
    }

    /**
     * Trừ số lượng tồn kho
     */
    private void truTonKho(
            List<ChiTietDonHang> chiTietDonHangs) {

        for (ChiTietDonHang ct : chiTietDonHangs) {

            if (ct.getIdSanPham() == null) {
                continue;
            }

            SanPham sanPham = sanPhamRepository
                    .findById(ct.getIdSanPham())
                    .orElseThrow(() -> new RuntimeException(
                            "Không tìm thấy sản phẩm"));

            int soLuongMua = ct.getSoLuongSanPham();

            sanPham.setSoTonKho(
                    sanPham.getSoTonKho() - soLuongMua);

            sanPhamRepository.save(sanPham);
        }
    }

    /**
     * Tạo thẻ tập cho những chi tiết đơn hàng là gói tập
     */
    private void taoTheTap(
            DonDangKy donDangKy,
            List<ChiTietDonHang> chiTietHangs) {

        Long now = System.currentTimeMillis();

        TheTap theTap = theTapRespository
                .findFirstByIdKhachHang(
                        donDangKy.getIdKhachHang());

        if (theTap == null) {

            theTap = new TheTap();

            theTap.setId(Generator.generate());

            theTap.setIdKhachHang(
                    donDangKy.getIdKhachHang());

            theTap.setQrCode(
                    GenarateCode.generate());

            theTap.setTrangThai(1);

            theTap.setNgayTao(now);

            theTapRespository.save(theTap);
        }

        for (ChiTietDonHang ct : chiTietHangs) {

            // Không phải gói tập
            if (ct.getIdGoiTap() == null) {
                continue;
            }

            GoiTap goiTap = goiTapRespository
                    .findFirstById(ct.getIdGoiTap());

            if (goiTap == null) {
                throw new RuntimeException(
                        "Không tìm thấy gói tập: "
                                + ct.getIdGoiTap());
            }

            TheTapGoiTap theTapGoiTap = new TheTapGoiTap();

            theTapGoiTap.setId(
                    Generator.generate());

            theTapGoiTap.setIdTheTap(
                    theTap.getId());

            theTapGoiTap.setIdGoiTap(
                    ct.getIdGoiTap());

            theTapGoiTap.setNgayBatDau(now);

            theTapGoiTap.setNgayKetThuc(
                    now
                            + goiTap.getSoNgay()
                                    * 24
                                    * 60
                                    * 60
                                    * 1000L);

            theTapGoiTap.setSoNgayConLai(
                    goiTap.getSoNgay());

            theTapGoiTap.setTrangThai(1);

            theTapGoiTap.setNgayTao(now);

            theTapGoiTapRepository.save(
                    theTapGoiTap);
        }
    }
}
