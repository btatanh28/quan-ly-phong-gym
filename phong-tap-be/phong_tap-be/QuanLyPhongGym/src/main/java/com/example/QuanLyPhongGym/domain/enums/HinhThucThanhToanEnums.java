package com.example.QuanLyPhongGym.domain.enums;

import java.util.Map;

public enum HinhThucThanhToanEnums {
    THANHTOANCHUYENKHOAN(1, "Thanh toán chuyển khoản"),
    THANHTOANTIENMAT(2, "Thanh toán tiền mặt");

    public static final Map<Integer, HinhThucThanhToanEnums> dict = Map.of(
            HinhThucThanhToanEnums.THANHTOANCHUYENKHOAN.value, HinhThucThanhToanEnums.THANHTOANCHUYENKHOAN,
            HinhThucThanhToanEnums.THANHTOANTIENMAT.value, HinhThucThanhToanEnums.THANHTOANTIENMAT);

    public final Integer value;
    public final String label;

    HinhThucThanhToanEnums(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
