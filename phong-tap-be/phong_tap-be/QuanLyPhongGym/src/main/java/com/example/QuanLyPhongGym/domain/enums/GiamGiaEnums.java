package com.example.QuanLyPhongGym.domain.enums;

import java.util.Map;

public enum GiamGiaEnums {
    NAMPHANTRAM(1, "5%"),
    MUOIPHANTRAM(2, "10%"),
    MUOILAMPHANTRAM(3, "15%"),
    HAIMUOIPHANTRAM(4, "20%"),
    HAILAMPHANTRAM(4, "25%"),
    NAMMUOIPHANTRAM(4, "50%");

    public static final Map<Integer, GiamGiaEnums> dict = Map.of(
            GiamGiaEnums.NAMPHANTRAM.value, GiamGiaEnums.NAMPHANTRAM,
            GiamGiaEnums.MUOIPHANTRAM.value, GiamGiaEnums.MUOIPHANTRAM,
            GiamGiaEnums.MUOILAMPHANTRAM.value, GiamGiaEnums.MUOILAMPHANTRAM,
            GiamGiaEnums.HAIMUOIPHANTRAM.value, GiamGiaEnums.HAIMUOIPHANTRAM,
            GiamGiaEnums.HAILAMPHANTRAM.value, GiamGiaEnums.HAILAMPHANTRAM,
            GiamGiaEnums.NAMMUOIPHANTRAM.value, GiamGiaEnums.NAMMUOIPHANTRAM);

    public final Integer value;
    public final String label;

    GiamGiaEnums(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
