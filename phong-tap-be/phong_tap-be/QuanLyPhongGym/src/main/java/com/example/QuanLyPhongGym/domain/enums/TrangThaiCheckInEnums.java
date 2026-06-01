package com.example.QuanLyPhongGym.domain.enums;

import java.util.Map;

public enum TrangThaiCheckInEnums {
    DACHECKINHOMNAY(0, "Đã Check-in Hôm Nay"),
    CHECKINTHANHCONG(1, "Check-in Thành Công");

    public static final Map<Integer, TrangThaiCheckInEnums> dict = Map.of(
            TrangThaiCheckInEnums.CHECKINTHANHCONG.value, TrangThaiCheckInEnums.CHECKINTHANHCONG,
            TrangThaiCheckInEnums.DACHECKINHOMNAY.value, TrangThaiCheckInEnums.DACHECKINHOMNAY);

    public final Integer value;
    public final String label;

    TrangThaiCheckInEnums(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
