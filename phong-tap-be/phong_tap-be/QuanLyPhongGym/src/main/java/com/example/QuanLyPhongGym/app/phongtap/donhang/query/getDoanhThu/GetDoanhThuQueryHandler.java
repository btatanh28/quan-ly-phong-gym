package com.example.QuanLyPhongGym.app.phongtap.donhang.query.getDoanhThu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.core.mediator.IRequestHandler;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetDoanhThuQueryHandler implements IRequestHandler<GetDoanhThuQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetDoanhThuQuery request) {

        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        // 👉 nếu có tháng → theo ngày
        if (request.getMonth() != null) {
            sql.append("""
                        SELECT
                            DAY(FROM_UNIXTIME(dh.NGAY_MUA/1000)) AS ngay,
                            MONTH(FROM_UNIXTIME(dh.NGAY_MUA/1000)) AS thang,
                            YEAR(FROM_UNIXTIME(dh.NGAY_MUA/1000)) AS nam,
                            SUM(dh.TONG_TIEN) AS tongTienDoanhThu
                        FROM DON_DANG_KY dh
                        WHERE dh.TRANG_THAI_SAN_PHAM = 2
                        AND YEAR(FROM_UNIXTIME(dh.NGAY_MUA/1000)) = ?
                        AND MONTH(FROM_UNIXTIME(dh.NGAY_MUA/1000)) = ?
                        GROUP BY ngay, thang, nam
                        ORDER BY ngay
                    """);

            params.add(request.getYear());
            params.add(request.getMonth());
        } else if (request.getYear() != null) {
            // 👉 theo tháng
            sql.append("""
                        SELECT
                            MONTH(FROM_UNIXTIME(dh.NGAY_MUA/1000)) AS thang,
                            YEAR(FROM_UNIXTIME(dh.NGAY_MUA/1000)) AS nam,
                            SUM(dh.TONG_TIEN) AS tongTienDoanhThu
                        FROM DON_DANG_KY dh
                        WHERE dh.TRANG_THAI_SAN_PHAM = 2
                        AND YEAR(FROM_UNIXTIME(dh.NGAY_MUA/1000)) = ?
                        GROUP BY thang, nam
                        ORDER BY thang
                    """);

            params.add(request.getYear());
        }

        List<GetDoanhThuQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetDoanhThuQueryDTO dto = new GetDoanhThuQueryDTO();

                    if (request.getMonth() != null) {
                        dto.setNgay(rs.getInt("ngay"));
                        dto.setThang(rs.getInt("thang"));
                        dto.setNam(rs.getInt("nam"));
                    } else {
                        dto.setThang(rs.getInt("thang"));
                        dto.setNam(rs.getInt("nam"));
                    }

                    dto.setTongTienDoanhThu(rs.getBigDecimal("tongTienDoanhThu"));

                    return dto;
                });

        ListResponse response = new ListResponse();

        response.setItems(items);

        return response;
    }
}
