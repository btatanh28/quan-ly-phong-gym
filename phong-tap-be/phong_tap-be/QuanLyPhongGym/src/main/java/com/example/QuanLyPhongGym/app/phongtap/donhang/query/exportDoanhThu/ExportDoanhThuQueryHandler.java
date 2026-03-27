package com.example.QuanLyPhongGym.app.phongtap.donhang.query.exportDoanhThu;

import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.InputStreamResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Lazy
@RequiredArgsConstructor
public class ExportDoanhThuQueryHandler {

    private final JdbcTemplate jdbcTemplate;

    public InputStreamResource export(ExportDoanhThuQuery request) {

        StringBuilder sql = new StringBuilder();

        // ========================
        // 🔥 SELECT
        // ========================
        sql.append("SELECT ");

        if (request.getMonth() != null) {
            sql.append(" DAY(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) as ngay, ");
        } else {
            sql.append(" NULL as ngay, "); // FIX lỗi group by
        }

        sql.append(" MONTH(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) as thang, ");
        sql.append(" YEAR(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) as nam, ");
        sql.append(" SUM(d.TONG_TIEN) as tongTien ");

        sql.append("FROM DON_DANG_KY d ");
        sql.append("WHERE d.TRANG_THAI_SAN_PHAM = 2 ");

        // ========================
        // 🔥 PARAMS
        // ========================
        List<Object> params = new ArrayList<>();

        if (request.getMonth() != null) {
            sql.append(" AND MONTH(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) = ? ");
            params.add(request.getMonth());
        }

        if (request.getYear() != null) {
            sql.append(" AND YEAR(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) = ? ");
            params.add(request.getYear());
        }

        // ========================
        // 🔥 GROUP BY
        // ========================
        if (request.getMonth() != null) {
            sql.append(" GROUP BY DAY(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)), ");
            sql.append(" MONTH(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)), ");
            sql.append(" YEAR(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) ");
            sql.append(" ORDER BY nam, thang, ngay ");
        } else {
            sql.append(" GROUP BY MONTH(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)), ");
            sql.append(" YEAR(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) ");
            sql.append(" ORDER BY nam, thang ");
        }

        // ========================
        // 🔥 QUERY
        // ========================
        List<ExportDoanhThuQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    ExportDoanhThuQueryDTO dto = new ExportDoanhThuQueryDTO();
                    dto.setStt(rowNum + 1);
                    dto.setNgay(rs.getString("ngay")); // có thể null
                    dto.setThang(rs.getInt("thang"));
                    dto.setNam(rs.getInt("nam"));
                    dto.setTongTien(rs.getDouble("tongTien"));
                    return dto;
                });

        if (items.isEmpty()) {
            throw new RuntimeException("Không có dữ liệu doanh thu");
        }

        // ========================
        // 🔥 BUILD CSV
        // ========================
        StringBuilder sb = new StringBuilder();

        // Title
        String title;
        if (request.getMonth() != null && request.getYear() != null) {
            title = "Báo cáo doanh thu tháng " + request.getMonth() + " năm " + request.getYear();
        } else if (request.getYear() != null) {
            title = "Báo cáo doanh thu năm " + request.getYear();
        } else {
            title = "Báo cáo doanh thu tất cả";
        }

        sb.append(title).append("\n\n");

        // Header
        if (request.getMonth() != null) {

            sb.append("STT,Ngày,Tháng,Năm,Tổng tiền\n");

            for (ExportDoanhThuQueryDTO item : items) {
                sb.append(item.getStt()).append(",");
                sb.append(item.getNgay() != null ? item.getNgay() : "").append(",");
                sb.append(item.getThang()).append(",");
                sb.append(item.getNam()).append(",");
                sb.append(item.getTongTien()).append("\n");
            }

        } else {

            sb.append("STT,Năm,Tổng tiền\n");

            for (ExportDoanhThuQueryDTO item : items) {
                sb.append(item.getStt()).append(",");
                sb.append(item.getNam()).append(",");
                sb.append(item.getTongTien()).append("\n");
            }
        }

        // BOM UTF-8 để Excel không lỗi font
        byte[] bytes = ("\uFEFF" + sb.toString()).getBytes(StandardCharsets.UTF_8);

        return new InputStreamResource(new ByteArrayInputStream(bytes));
    }
}
