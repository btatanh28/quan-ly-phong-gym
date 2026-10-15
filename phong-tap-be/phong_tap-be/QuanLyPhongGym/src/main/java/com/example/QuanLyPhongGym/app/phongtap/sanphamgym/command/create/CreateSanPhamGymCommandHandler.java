package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.create;

import java.math.BigDecimal;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.Cloudinary.CloudinaryStorageService;
import com.example.QuanLyPhongGym.domain.entity.app.sanpham.SanPham;
import com.example.QuanLyPhongGym.domain.repository.app.sanpham.SanPhamRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class CreateSanPhamGymCommandHandler {
    private final SanPhamRepository repository;

    private final CloudinaryStorageService cloudinaryStorageService;

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

        String hinhAnh = request.getHinhAnh();

        if (hinhAnh != null && !hinhAnh.isBlank()) {
            try {
                if (hinhAnh.startsWith("data:image")) {
                    hinhAnh = cloudinaryStorageService.uploadBase64Image(
                            hinhAnh,
                            "quan-ly-phong-gym/san-pham");
                }

                sanPhamGym.setHinhAnh(hinhAnh);

            } catch (Exception e) {
                throw new RuntimeException(
                        "Không thể upload ảnh sản phẩm lên Cloudinary", e);
            }
        }

        repository.save(sanPhamGym);

        return new DataResponse(sanPhamGym.getId());
    }
}
