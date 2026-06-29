package com.example.QuanLyPhongGym.domain.enums;

import java.util.Map;

public enum TrangThaiSanPhamEnums {
    DANGXULY(1, "Đang Xử Lý"),
    DATHANHTOAN(2, "Đã Thanh Toán"),
    CHOTHANHTOAN(3, "Chờ Thanh Toán"),
    DAHUY(4, "Đã Hủy");

    public static final Map<Integer, TrangThaiSanPhamEnums> dict = Map.of(
            TrangThaiSanPhamEnums.DANGXULY.value, TrangThaiSanPhamEnums.DANGXULY,
            TrangThaiSanPhamEnums.DATHANHTOAN.value, TrangThaiSanPhamEnums.DATHANHTOAN,
            TrangThaiSanPhamEnums.CHOTHANHTOAN.value, TrangThaiSanPhamEnums.CHOTHANHTOAN,
            TrangThaiSanPhamEnums.DAHUY.value, TrangThaiSanPhamEnums.DAHUY);

    public final Integer value;
    public final String label;

    TrangThaiSanPhamEnums(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
