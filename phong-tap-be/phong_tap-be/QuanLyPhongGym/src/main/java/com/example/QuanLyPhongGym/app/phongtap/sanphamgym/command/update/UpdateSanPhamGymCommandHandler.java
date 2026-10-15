package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.update;

import java.math.BigDecimal;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.service.Cloudinary.CloudinaryStorageService;
import com.example.QuanLyPhongGym.domain.entity.app.sanpham.SanPham;
import com.example.QuanLyPhongGym.domain.repository.app.sanpham.SanPhamRepository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class UpdateSanPhamGymCommandHandler {
    private final SanPhamRepository repository;
    private final CloudinaryStorageService cloudinaryStorageService;

    public DataResponse handle(UpdateSanPhamGymCommand request) {
        SanPham sanPham = repository.findFirstById(request.getId());

        if (request.getId() == null) {
            throw new NotFoundException("Không tìm thấy sản phẩm");
        }

        sanPham.setTenSanPham(request.getTenSanPham());
        sanPham.setSoTonKho(request.getSoTonKho());
        sanPham.setHinhAnh(request.getHinhAnh());
        sanPham.setGia(request.getGia());
        sanPham.setMoTa(request.getMoTa());
        sanPham.setGiamGia(request.getGiamGia());

        if (request.getGiamGia() != null && request.getGiamGia() == 1) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.05));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPham.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 2) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.1));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPham.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 3) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.15));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPham.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 4) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.2));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPham.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 5) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.25));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPham.setGiaSauGiam(giaSauGiam);
        } else if (request.getGiamGia() != null && request.getGiamGia() == 6) {

            BigDecimal gia = request.getGia();
            BigDecimal tienGiam = gia.multiply(BigDecimal.valueOf(0.5));

            BigDecimal giaSauGiam = gia.subtract(tienGiam);

            sanPham.setGiaSauGiam(giaSauGiam);
        }

        String hinhAnh = request.getHinhAnh();

        if (hinhAnh != null && !hinhAnh.isBlank()) {
            try {
                if (hinhAnh.startsWith("data:image")) {
                    hinhAnh = cloudinaryStorageService.uploadBase64Image(
                            hinhAnh,
                            "quan-ly-phong-gym/san-pham");
                }

                sanPham.setHinhAnh(hinhAnh);

            } catch (Exception e) {
                throw new RuntimeException(
                        "Không thể upload ảnh sản phẩm lên Cloudinary", e);
            }
        }

        repository.save(sanPham);

        return new DataResponse(sanPham.getId());
    }
}
