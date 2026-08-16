package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.create;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.domain.entity.app.sanpham.SanPham;
import com.example.QuanLyPhongGym.domain.repository.app.sanpham.SanPhamRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateSanPhamGymCommandHandler {
    private final SanPhamRepository repository;

    public DataResponse handle(CreateSanPhamGymCommand request) {
        SanPham sanPhamGym = new SanPham();
        sanPhamGym.setId(request.getId());
        sanPhamGym.setTenSanPham(request.getTenSanPham());
        sanPhamGym.setHinhAnh(request.getHinhAnh());
        sanPhamGym.setGia(request.getGia());
        sanPhamGym.setSoTonKho(request.getSoTonKho());
        sanPhamGym.setMoTa(request.getMoTa());
        sanPhamGym.setGiamGia(request.getGiamGia());
        sanPhamGym.setGiaSauGiam(request.getGiaSauGiam());

        if (request.getGiamGia() != null && request.getGiamGia() == 1) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.05));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPhamGym.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 2) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.1));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPhamGym.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 3) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.15));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPhamGym.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 4) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.2));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPhamGym.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 5) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.25));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPhamGym.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 6) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.5));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPhamGym.setGiaSauGiam(giaSauGiam);
        }

        if (request.getHinhAnh() != null && request.getHinhAnh().startsWith("data:image")) {
            try {
                String base64 = request.getHinhAnh().split(",")[1]; // bỏ phần data:image/png;base64,
                byte[] bytes = Base64.getDecoder().decode(base64);

                // Lưu file vào thư mục uploads/user
                Path path = Paths.get("uploads/san-pham-gym/" + sanPhamGym.getId() + ".png");
                Files.createDirectories(path.getParent());
                Files.write(path, bytes);

                // Lưu đường dẫn vào DB
                sanPhamGym.setHinhAnh("/uploads/san-pham-gym/" + sanPhamGym.getId() + ".png");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        repository.save(sanPhamGym);

        return new DataResponse(sanPhamGym.getId());
    }
}
