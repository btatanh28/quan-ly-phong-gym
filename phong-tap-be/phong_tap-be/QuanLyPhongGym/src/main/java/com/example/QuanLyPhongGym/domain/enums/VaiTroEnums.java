package com.example.QuanLyPhongGym.domain.enums;

import java.util.Map;

public enum VaiTroEnums {
    SUPERADMIN(1, "Super Admin"),
    ADMIN(2, "Admin"),
    QUANLY(3, "Quản Lý"),
    KETOAN(4, "Kế Toán"),
    NHANVIEN(5, "Nhân Viên"),
    THUNGAN(6, "Thu Ngân"),
    KHACHHANG(7, "Khách Hàng");

    public static final Map<Integer, VaiTroEnums> dict = Map.of(
            VaiTroEnums.SUPERADMIN.value, VaiTroEnums.SUPERADMIN,
            VaiTroEnums.ADMIN.value, VaiTroEnums.ADMIN,
            VaiTroEnums.QUANLY.value, VaiTroEnums.QUANLY,
            VaiTroEnums.KETOAN.value, VaiTroEnums.KETOAN,
            VaiTroEnums.NHANVIEN.value, VaiTroEnums.NHANVIEN,
            VaiTroEnums.THUNGAN.value, VaiTroEnums.THUNGAN,
            VaiTroEnums.KHACHHANG.value, VaiTroEnums.KHACHHANG);

    public final Integer value;
    public final String label;

    VaiTroEnums(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
