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
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    gt.ID, " +
                        "    tp.ID_KHACH_HANG, " +
                        "    kh.TEN_KHACH_HANG, " +
                        "    gt.TEN_GOI_TAP, " +
                        "    ct.ID_GOI_TAP, " +
                        "    kh.SO_DIEN_THOAI, " +
                        "    kh.EMAIL, " +
                        "    ct.SO_NGAY_CON_LAI " +
                        "FROM THE_TAP tp " +
                        "JOIN KHACH_HANG kh " +
                        " ON tp.ID_KHACH_HANG = kh.ID " +
                        "LEFT JOIN THE_TAP_GOI_TAP ct " +
                        " ON ct.ID_THE_TAP = tp.ID " +
                        "LEFT JOIN GOI_TAP gt " +
                        " ON ct.ID_GOI_TAP = gt.ID " +
                        "WHERE 1=1 ");

        StringBuilder countSql = new StringBuilder("""
                    SELECT COUNT(*)
                    FROM GOI_TAP gt
                    WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();
        List<Object> countParams = new ArrayList<>();

        boolean isFilter = false;

        if (request.getTenKhachHang() != null && !request.getTenKhachHang().isEmpty()) {
            isFilter = true;
            sql.append(" AND kh.TEN_KHACH_HANG LIKE ?");
            params.add("%" + request.getTenKhachHang() + "%");
        }

        if (request.getSoDienThoai() != null) {
            isFilter = true;
            sql.append(" AND kh.SO_DIEN_THOAI = ?");
            params.add(request.getSoDienThoai());
        }

        if (request.getEmail() != null) {
            isFilter = true;
            sql.append(" AND kh.EMAIL = ?");
            params.add(request.getEmail());
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

        List<GetListTheTapQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListTheTapQueryDTO dto = new GetListTheTapQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setTenGoiTap(rs.getString("TEN_GOI_TAP"));
                    dto.setEmail(rs.getString("EMAIL"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    dto.setSoNgayConLai(rs.getInt("SO_NGAY_CON_LAI"));
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
