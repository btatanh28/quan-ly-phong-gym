package com.example.QuanLyPhongGym.app.phongtap.sanpham.command.update;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateSanPhamCommandHandler {
    private final GoiTapRespository repository;

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

                case 2: // 2 tháng
                    goiTap.setSoNgay(60);
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

        if (request.getHinhAnh() != null && request.getHinhAnh().startsWith("data:image")) {
            try {
                String base64 = request.getHinhAnh().split(",")[1]; // bỏ phần data:image/png;base64,
                byte[] bytes = Base64.getDecoder().decode(base64);

                // Lưu file vào thư mục uploads/user
                Path path = Paths.get("uploads/san-pham/" + goiTap.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                // Lưu đường dẫn vào DB
                goiTap.setHinhAnh("/uploads/san-pham/" + goiTap.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        repository.save(goiTap);

        return new DataResponse(goiTap.getId());
    }
}
