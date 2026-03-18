package com.example.QuanLyPhongGym.app.phongtap.thetap.query.getlist;

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

public class GetListTheTapQueryHandler implements IRequestHandler<GetListTheTapQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListTheTapQuery request) {
        StringBuilder sql = new StringBuilder("""
                    SELECT
                        tp.ID_KHACH_HANG,
                        kh.TEN_KHACH_HANG,
                        kh.SO_DIEN_THOAI,
                        kh.EMAIL,
                        SUM(ct.SO_NGAY_CON_LAI) AS SO_NGAY_CON_LAI,
                        GROUP_CONCAT(gt.TEN_GOI_TAP) AS TEN_GOI_TAP
                    FROM THE_TAP tp
                    JOIN KHACH_HANG kh ON tp.ID_KHACH_HANG = kh.ID
                    LEFT JOIN THE_TAP_GOI_TAP ct ON ct.ID_THE_TAP = tp.ID
                    LEFT JOIN GOI_TAP gt ON ct.ID_GOI_TAP = gt.ID
                    WHERE 1=1
                """);

        StringBuilder countSql = new StringBuilder("""
                    SELECT COUNT(DISTINCT tp.ID_KHACH_HANG)
                    FROM THE_TAP tp
                    JOIN KHACH_HANG kh ON tp.ID_KHACH_HANG = kh.ID
                    LEFT JOIN THE_TAP_GOI_TAP ct ON ct.ID_THE_TAP = tp.ID
                    LEFT JOIN GOI_TAP gt ON ct.ID_GOI_TAP = gt.ID
                    WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();
        List<Object> countParams = new ArrayList<>();

        // ===== FILTER =====
        if (request.getTenKhachHang() != null && !request.getTenKhachHang().isEmpty()) {
            sql.append(" AND kh.TEN_KHACH_HANG LIKE ?");
            countSql.append(" AND kh.TEN_KHACH_HANG LIKE ?");
            String value = "%" + request.getTenKhachHang() + "%";
            params.add(value);
            countParams.add(value);
        }

        if (request.getSoDienThoai() != null) {
            sql.append(" AND kh.SO_DIEN_THOAI = ?");
            countSql.append(" AND kh.SO_DIEN_THOAI = ?");
            params.add(request.getSoDienThoai());
            countParams.add(request.getSoDienThoai());
        }

        if (request.getEmail() != null) {
            sql.append(" AND kh.EMAIL = ?");
            countSql.append(" AND kh.EMAIL = ?");
            params.add(request.getEmail());
            countParams.add(request.getEmail());
        }

        // ===== GROUP BY =====
        sql.append("""
                    GROUP BY
                        tp.ID_KHACH_HANG,
                        kh.TEN_KHACH_HANG,
                        kh.SO_DIEN_THOAI,
                        kh.EMAIL
                """);

        // ===== PAGING =====
        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 10;
        int offset = page * size;

        sql.append(" ORDER BY tp.ID_KHACH_HANG DESC LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        // ===== QUERY DATA =====
        List<GetListTheTapQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListTheTapQueryDTO dto = new GetListTheTapQueryDTO();
                    dto.setId(rs.getString("ID_KHACH_HANG"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setTenGoiTap(rs.getString("TEN_GOI_TAP")); // đã concat
                    dto.setEmail(rs.getString("EMAIL"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    dto.setSoNgayConLai(rs.getInt("SO_NGAY_CON_LAI")); // đã SUM
                    return dto;
                });

        // ===== COUNT =====
        Long totalItems = jdbcTemplate.queryForObject(
                countSql.toString(),
                countParams.toArray(),
                Long.class);

        // ===== RESPONSE =====
        ListResponse response = new ListResponse();
        response.setItems(items);
        response.setTotalItems(totalItems);
        response.setPage(page);
        response.setSize(size);
        response.setTotalPages((int) Math.ceil((double) totalItems / size));

        return response;
    }
}
