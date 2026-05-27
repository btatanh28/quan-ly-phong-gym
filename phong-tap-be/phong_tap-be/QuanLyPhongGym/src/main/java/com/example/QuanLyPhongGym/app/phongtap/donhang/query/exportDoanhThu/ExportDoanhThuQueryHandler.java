package com.example.QuanLyPhongGym.app.phongtap.donhang.query.exportDoanhThu;

import lombok.RequiredArgsConstructor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

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

        sql.append("SELECT ");

        if (request.getMonth() != null) {
            sql.append("DAY(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) as ngay, ");
        } else {
            sql.append("NULL as ngay, ");
        }

        sql.append("MONTH(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) as thang, ");
        sql.append("YEAR(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) as nam, ");
        sql.append("SUM(d.TONG_TIEN) as tongTien ");

        sql.append("FROM DON_DANG_KY d ");
        sql.append("WHERE d.TRANG_THAI_SAN_PHAM = 2 ");

        List<Object> params = new ArrayList<>();

        if (request.getMonth() != null) {
            sql.append(" AND MONTH(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) = ? ");
            params.add(request.getMonth());
        }

        if (request.getYear() != null) {
            sql.append(" AND YEAR(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)) = ? ");
            params.add(request.getYear());
        }

        if (request.getMonth() != null) {

            sql.append("""
                    GROUP BY
                    DAY(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)),
                    MONTH(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)),
                    YEAR(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000))
                    ORDER BY nam, thang, ngay
                    """);

        } else {

            sql.append("""
                    GROUP BY
                    MONTH(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000)),
                    YEAR(FROM_UNIXTIME(d.NGAY_THANH_TOAN / 1000))
                    ORDER BY nam, thang
                    """);
        }

        List<ExportDoanhThuQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {

                    ExportDoanhThuQueryDTO dto = new ExportDoanhThuQueryDTO();

                    dto.setStt(rowNum + 1);
                    dto.setNgay(rs.getString("ngay"));
                    dto.setThang(rs.getInt("thang"));
                    dto.setNam(rs.getInt("nam"));
                    dto.setTongTienDoanhThu(rs.getDouble("tongTien"));

                    return dto;
                });

        if (items.isEmpty()) {
            throw new RuntimeException("Không có dữ liệu doanh thu");
        }

        try {

            InputStream inputStream = getClass()
                    .getResourceAsStream("/template/doanh-thu-template.xlsx");

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            // TITLE
            Row titleRow = sheet.getRow(0);

            String title;

            if (request.getMonth() != null && request.getYear() != null) {
                title = "BÁO CÁO DOANH THU THÁNG "
                        + request.getMonth()
                        + " NĂM "
                        + request.getYear();
            } else if (request.getYear() != null) {
                title = "BÁO CÁO DOANH THU NĂM " + request.getYear();
            } else {
                title = "BÁO CÁO DOANH THU";
            }

            titleRow.getCell(0).setCellValue(title);

            // DATA
            int startRow = 2;

            // dòng mẫu có style sẵn trong excel
            Row templateRow = sheet.getRow(startRow);

            if (items.size() > 1) {

                sheet.shiftRows(
                        startRow + 1,
                        sheet.getLastRowNum(),
                        items.size() - 1,
                        true,
                        false);
            }

            for (int i = 0; i < items.size(); i++) {

                ExportDoanhThuQueryDTO item = items.get(i);

                Row row = sheet.getRow(startRow + i);

                // nếu chưa có dòng thì tạo mới
                if (row == null) {
                    row = sheet.createRow(startRow + i);
                }

                row.setHeight(templateRow.getHeight());

                for (int j = 0; j < templateRow.getLastCellNum(); j++) {

                    // cell mẫu
                    var templateCell = templateRow.getCell(j);

                    if (templateCell == null)
                        continue;

                    // cell mới
                    var cell = row.getCell(j);

                    if (cell == null) {
                        cell = row.createCell(j);
                    }

                    // copy style
                    cell.setCellStyle(templateCell.getCellStyle());
                }

                // đổ dữ liệu
                row.getCell(0).setCellValue(item.getStt());

                if (request.getMonth() != null) {
                    row.getCell(1).setCellValue(item.getNgay() == null ? "" : item.getNgay());
                    row.getCell(2).setCellValue(item.getThang());
                    row.getCell(3).setCellValue(item.getNam());
                    row.getCell(4).setCellValue(item.getTongTienDoanhThu());

                } else if (request.getYear() != null) {
                    row.getCell(1).setCellValue(item.getNgay() == null ? "" : item.getNgay());
                    row.getCell(2).setCellValue(item.getThang());
                    row.getCell(3).setCellValue(item.getNam());
                    row.getCell(4).setCellValue(item.getTongTienDoanhThu());
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            workbook.write(out);

            workbook.close();

            return new InputStreamResource(
                    new ByteArrayInputStream(out.toByteArray()));

        } catch (Exception e) {
            throw new RuntimeException("Lỗi export excel", e);
        }
    }
}