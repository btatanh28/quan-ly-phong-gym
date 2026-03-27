package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.getChiTietDoanhThu;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetChiTietDoanhThuQueryHandler {
    private final JdbcTemplate jdbcTemplate;

    public ListResponse handle(GetChiTietDoanhThuQuery request) {

        String sql = """
                    SELECT
                        ct.ID_GOI_TAP,
                        gt.TEN_GOI_TAP,
                        SUM(ct.SO_LUONG) AS soLuong,
                        SUM(ct.GIA) AS tongTien
                    FROM CHI_TIET_DON_HANG ct
                    JOIN DON_DANG_KY dh ON ct.ID_DON_HANG = dh.ID
                    JOIN GOI_TAP gt ON ct.ID_GOI_TAP = gt.ID
                    WHERE dh.TRANG_THAI_SAN_PHAM = 2
                    GROUP BY ct.ID_GOI_TAP, gt.TEN_GOI_TAP
                    ORDER BY tongTien DESC
                """;

        List<GetChiTietDoanhThuQueryDTO> items = jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    GetChiTietDoanhThuQueryDTO dto = new GetChiTietDoanhThuQueryDTO();
                    dto.setIdGoiTap(rs.getString("ID_GOI_TAP"));
                    dto.setTenGoiTap(rs.getString("TEN_GOI_TAP"));
                    dto.setSoLuong(rs.getInt("soLuong"));
                    dto.setTongTien(rs.getBigDecimal("tongTien"));
                    return dto;
                });

        ListResponse response = new ListResponse();
        response.setItems(items);

        return response;
    }
}
