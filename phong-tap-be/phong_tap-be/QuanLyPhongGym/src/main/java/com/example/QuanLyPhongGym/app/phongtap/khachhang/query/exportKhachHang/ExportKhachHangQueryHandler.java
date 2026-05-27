package com.example.QuanLyPhongGym.app.phongtap.khachhang.query.exportKhachHang;

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
public class ExportKhachHangQueryHandler {

    private final JdbcTemplate jdbcTemplate;

    public InputStreamResource export(ExportKhachHangQuery request) {

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ");
        sql.append("ID, TEN_KHACH_HANG, DIA_CHI, EMAIL, SO_DIEN_THOAI, Da_XAC_NHAN, MA_XAC_NHAN ");
        sql.append("FROM KHACH_HANG WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        List<ExportKhachHangQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {

                    ExportKhachHangQueryDTO dto = new ExportKhachHangQueryDTO();

                    dto.setStt(rowNum + 1);
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setEmail(rs.getString("EMAIL"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    dto.setDiaChi(rs.getString("DIA_CHI"));

                    return dto;
                });

        if (items.isEmpty()) {
            throw new RuntimeException("Không có dữ liệu khách hàng");
        }

        try {

            InputStream inputStream = getClass()
                    .getResourceAsStream("/template/danh-sach-khach-hang-template.xlsx");

            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            // TITLE
            Row titleRow = sheet.getRow(0);

            String title;

            title = "Danh sách khách hàng";

            titleRow.getCell(0).setCellValue(title);

            // DATA
            int startRow = 2;

            // dòng mẫu có style sẵn trong excel
            Row templateRow = sheet.getRow(startRow);

            for (int i = 0; i < items.size(); i++) {

                ExportKhachHangQueryDTO item = items.get(i);

                Row row = sheet.getRow(startRow + i);

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
                row.getCell(1).setCellValue(item.getTenKhachHang());
                row.getCell(2).setCellValue(item.getEmail());
                row.getCell(3).setCellValue(item.getSoDienThoai());
                row.getCell(4).setCellValue(item.getDiaChi());

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