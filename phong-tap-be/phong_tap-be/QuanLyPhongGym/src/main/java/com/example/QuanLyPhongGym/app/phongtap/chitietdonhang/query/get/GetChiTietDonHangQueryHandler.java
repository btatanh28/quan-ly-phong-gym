package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.get;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.exception.NotFoundException;
import com.example.QuanLyPhongGym.domain.repository.app.chitietdonhang.ChiTietDonHangRespository;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetChiTietDonHangQueryHandler {
    // private final ChiTietDonHangRespository respository;

    private final JdbcTemplate jdbcTemplate;

    public List<GetChiTietDonHangQueryDTO> handle(GetChiTietDonHangQuery request) {
        String sql = """
                    SELECT
                        ct.ID,
                        ct.ID_DON_HANG,
                        ct.ID_GOI_TAP,
                        gt.TEN_GOI_TAP,
                        ct.GIA,
                        ct.SO_LUONG,
                        ct.TONG_TIEN
                    FROM CHI_TIET_DON_HANG ct
                    JOIN GOI_TAP gt ON ct.ID_GOI_TAP = gt.ID
                    WHERE ct.ID_DON_HANG = ?
                """;

        List<GetChiTietDonHangQueryDTO> list = jdbcTemplate.query(
                sql,
                new Object[] { request.getId() },
                (rs, rowNum) -> {
                    GetChiTietDonHangQueryDTO dto = new GetChiTietDonHangQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setIdDonHang(rs.getString("ID_DON_HANG"));
                    dto.setIdGoiTap(rs.getString("ID_GOI_TAP"));
                    dto.setTenGoiTap(rs.getString("TEN_GOI_TAP"));
                    dto.setSoLuong(rs.getInt("SO_LUONG"));
                    dto.setGia(rs.getBigDecimal("GIA"));
                    dto.setTongTien(rs.getBigDecimal("TONG_TIEN"));
                    return dto;
                });

        if (list.isEmpty()) {
            throw new NotFoundException("Không tìm thấy chi tiết đơn hàng");
        }

        return list;
    }
}
