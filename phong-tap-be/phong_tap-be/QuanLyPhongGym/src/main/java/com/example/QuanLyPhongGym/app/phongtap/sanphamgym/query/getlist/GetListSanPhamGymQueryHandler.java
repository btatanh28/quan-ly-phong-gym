package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getlist;

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

public class GetListSanPhamGymQueryHandler implements IRequestHandler<GetListSanPhamGymQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListSanPhamGymQuery request) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    spy.ID, " +
                        "    spy.TEN_SAN_PHAM, " +
                        "    spy.HINH_ANH, " +
                        "    spy.GIA, " +
                        "    spy.SO_TON_KHO, " +
                        "    spy.GIAM_GIA, " +
                        "    spy.MO_TA, " +
                        "    spy.GIA_SAU_GIAM " +
                        "FROM SAN_PHAM spy " +

                        "WHERE 1=1 ");

        StringBuilder countSql = new StringBuilder("""
                    SELECT COUNT(*)
                    FROM GOI_TAP spy
                    WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();
        List<Object> countParams = new ArrayList<>();

        boolean isFilter = false;

        if (request.getTenSanPham() != null && !request.getTenSanPham().isEmpty()) {
            isFilter = true;
            sql.append(" AND spy.TEN_SAN_PHAM LIKE ?");
            params.add("%" + request.getTenSanPham() + "%");
        }

        if (request.getGia() != null) {
            isFilter = true;
            sql.append(" AND spy.GIA = ?");
            params.add(request.getGia());
        }

        int page = 1;
        int size = 5;
        int offset = 0;

        if (!isFilter) {
            page = request.getPage() != null ? request.getPage() : 0;
            size = request.getSize() != null ? request.getSize() : request.getSize();
            offset = page * size;
            sql.append(" ORDER BY spy.ID DESC LIMIT ? OFFSET ?");
            params.add(size);
            params.add(offset);
        } else {
            // Nếu có filter, không phân trang
            sql.append(" ORDER BY spy.ID");
        }

        List<GetListSanPhamGymQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListSanPhamGymQueryDTO dto = new GetListSanPhamGymQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setTenSanPham(rs.getString("TEN_SAN_PHAM"));
                    dto.setGia(rs.getBigDecimal("GIA"));
                    dto.setGiaSauGiam(rs.getBigDecimal("GIA_SAU_GIAM"));
                    dto.setMoTa(rs.getString("MO_TA"));
                    dto.setGiamGia(rs.getInt("GIAM_GIA"));
                    dto.setSoTonKho(rs.getInt("SO_TON_KHO"));
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
