package com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getlist;

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

public class GetListSanPhamQueryHandler implements IRequestHandler<GetListSanPhamQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListSanPhamQuery request) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    gt.ID, " +
                        "    gt.TEN_GOI_TAP, " +
                        "    gt.HINH_ANH, " +
                        "    gt.GIA, " +
                        "    gt.THOI_HAN_NGAY, " +
                        "    gt.GIAM_GIA, " +
                        "    gt.MO_TA, " +
                        "    gt.SO_NGAY, " +
                        "    gt.GIA_SAU_GIAM " +
                        "FROM GOI_TAP gt " +

                        "WHERE 1=1 ");

        StringBuilder countSql = new StringBuilder("""
                    SELECT COUNT(*)
                    FROM GOI_TAP gt
                    WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();
        List<Object> countParams = new ArrayList<>();

        boolean isFilter = false;

        if (request.getTenGoiTap() != null && !request.getTenGoiTap().isEmpty()) {
            isFilter = true;
            sql.append(" AND gt.TEN_GOI_TAP LIKE ?");
            params.add("%" + request.getTenGoiTap() + "%");
        }

        if (request.getGia() != null) {
            isFilter = true;
            sql.append(" AND GIA = ?");
            params.add(request.getGia());
        }

        int page = 1;
        int size = 9;
        int offset = 0;

        if (!isFilter) {
            page = request.getPage() != null ? request.getPage() : 0;
            size = request.getSize() != null ? request.getSize() : request.getSize();
            offset = page * size;
            sql.append(" ORDER BY gt.ID DESC LIMIT ? OFFSET ?");
            params.add(size);
            params.add(offset);
        } else {
            // Nếu có filter, không phân trang
            sql.append(" ORDER BY gt.ID DESC");
        }

        List<GetListSanPhamQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListSanPhamQueryDTO dto = new GetListSanPhamQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setTenGoiTap(rs.getString("TEN_GOI_TAP"));
                    dto.setGia(rs.getBigDecimal("GIA"));
                    dto.setGiaSauGiam(rs.getBigDecimal("GIA_SAU_GIAM"));
                    dto.setMoTa(rs.getString("MO_TA"));
                    dto.setGiamGia(rs.getInt("GIAM_GIA"));
                    dto.setThoiHanNgay(rs.getInt("THOI_HAN_NGAY"));
                    dto.setSoNgay(rs.getInt("SO_NGAY"));
                    dto.setHinhAnh(rs.getString("HINH_ANH"));
                    return dto;
                });

        Long totalItems;

        if (isFilter) {
            totalItems = (long) items.size();
        } else {
            totalItems = jdbcTemplate.queryForObject(
                    countSql.toString(),
                    countParams.toArray(),
                    Long.class);
        }

        // Bao bọc thành ListResponse
        ListResponse response = new ListResponse();
        response.setItems(items);
        response.setTotalItems(totalItems);
        response.setPage(page);
        response.setSize(size);
        response.setTotalPages((int) Math.ceil((double) totalItems / size));

        return response;
    }
}
