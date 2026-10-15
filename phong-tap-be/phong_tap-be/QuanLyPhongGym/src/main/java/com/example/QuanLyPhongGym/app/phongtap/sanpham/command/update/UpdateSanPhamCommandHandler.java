package com.example.QuanLyPhongGym.app.phongtap.sanpham.command.update;

import java.math.BigDecimal;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.Cloudinary.CloudinaryStorageService;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateSanPhamCommandHandler {
    private final GoiTapRespository repository;
    private final CloudinaryStorageService cloudinaryStorageService;

    public DataResponse handle(UpdateSanPhamCommand request) {
        GoiTap goiTap = repository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không tìm thấy sản phẩm");
        }

        goiTap.setTenGoiTap(request.getTenGoiTap());
        goiTap.setThoiHanNgay(request.getThoiHanNgay());
        goiTap.setHinhAnh(request.getHinhAnh());
        goiTap.setGia(request.getGia());
        goiTap.setMoTa(request.getMoTa());
        goiTap.setGiamGia(request.getGiamGia());

        if (request.getGiamGia() != null && request.getGiamGia() == 1) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.05));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            goiTap.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 2) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.1));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            goiTap.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 3) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.15));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            goiTap.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 4) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.2));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            goiTap.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 5) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.25));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            goiTap.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 6) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.5));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            goiTap.setGiaSauGiam(giaSauGiam);
        }

        if (request.getThoiHanNgay() != null) {
            switch (request.getThoiHanNgay()) {
                case 1: // 1 ngày
                    goiTap.setSoNgay(1);
                    break;

                case 2: // 1 tháng
                    goiTap.setSoNgay(30);
                    break;

                case 3: // 3 tháng
                    goiTap.setSoNgay(90);
                    break;

                case 4: // 6 tháng
                    goiTap.setSoNgay(180);
                    break;

                case 5: // 1 năm
                    goiTap.setSoNgay(365);
                    break;

                case 6: // 2 năm
                    goiTap.setSoNgay(730);
                    break;

                default:
                    throw new IllegalArgumentException("Thời hạn không hợp lệ");
            }
        }

        String hinhAnh = request.getHinhAnh();

        if (hinhAnh != null && !hinhAnh.isBlank()) {
            try {
                if (hinhAnh.startsWith("data:image")) {
                    hinhAnh = cloudinaryStorageService.uploadBase64Image(
                            hinhAnh,
                            "quan-ly-phong-gym/san-pham");
                }

                goiTap.setHinhAnh(hinhAnh);

            } catch (Exception e) {
                throw new RuntimeException(
                        "Không thể upload ảnh gói tập lên Cloudinary", e);
            }
        }

        repository.save(goiTap);

        return new DataResponse(goiTap.getId());
    }
}
