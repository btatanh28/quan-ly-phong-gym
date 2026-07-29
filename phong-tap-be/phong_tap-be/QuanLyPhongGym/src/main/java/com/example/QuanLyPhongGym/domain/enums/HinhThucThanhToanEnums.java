package com.example.QuanLyPhongGym.domain.enums;

import java.util.Map;

public enum HinhThucThanhToanEnums {
    THANHTOANCHUYENKHOANMOMO(1, "Thanh toán chuyển khoản MoMo"),
    THANHTOANCHUYENKHOANVNPAY(2, "Thanh toán chuyển khoản VNPAY"),
    THANHTOANTIENMAT(3, "Thanh toán tiền mặt");

    public static final Map<Integer, HinhThucThanhToanEnums> dict = Map.of(
            HinhThucThanhToanEnums.THANHTOANCHUYENKHOANMOMO.value, HinhThucThanhToanEnums.THANHTOANCHUYENKHOANMOMO,
            HinhThucThanhToanEnums.THANHTOANCHUYENKHOANVNPAY.value, HinhThucThanhToanEnums.THANHTOANCHUYENKHOANVNPAY,
            HinhThucThanhToanEnums.THANHTOANTIENMAT.value, HinhThucThanhToanEnums.THANHTOANTIENMAT);

    public final Integer value;
    public final String label;

    HinhThucThanhToanEnums(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
