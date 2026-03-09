// package com.example.QuanLyPhongGym.app.phongtap.sanpham.query.kiemtratonkho;

// import org.springframework.context.annotation.Lazy;
// import org.springframework.stereotype.Component;

// import com.CuaHang.QuanLyCuaHang.domain.entity.app.sanpham.SanPham;
// import com.CuaHang.QuanLyCuaHang.domain.repository.app.sanpham.SanPhamRespository;

// import lombok.RequiredArgsConstructor;

// @Component
// @Lazy
// @RequiredArgsConstructor

// public class KiemTraTonKhoQueryHandler {
//     private final GoiTapRespository respository;

//     public KiemTraTonKhoQueryDTO handle(KiemTraTonKhoQuery request) {
//         GoiTap sanPham = respository.findFirstById(request.getId());

//         if (sanPham == null) {
//             return null;
//         }
//         KiemTraTonKhoQueryDTO dto = new KiemTraTonKhoQueryDTO();
//         dto.setId(sanPham.getId());
//         dto.setSoLuong(sanPham.getSoTonKho());
//         return dto;
//     }
// }
