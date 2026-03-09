package com.example.QuanLyPhongGym.domain.enums;

import java.util.Map;

public enum HinhThucThanhToanEnums {
    THANHTOANTIENMAT(1, "Thanh toán tiền mặt"),
    THANHTOANCHUYENKHOAN(2, "Thanh toán chuyển khoản");

    public static final Map<Integer, HinhThucThanhToanEnums> dict = Map.of(
            HinhThucThanhToanEnums.THANHTOANTIENMAT.value, HinhThucThanhToanEnums.THANHTOANTIENMAT,
            HinhThucThanhToanEnums.THANHTOANCHUYENKHOAN.value, HinhThucThanhToanEnums.THANHTOANCHUYENKHOAN);

    public final Integer value;
    public final String label;

    HinhThucThanhToanEnums(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
