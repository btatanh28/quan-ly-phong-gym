package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.getList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getlist.GetListSanPhamQueryDTO;
import com.example.QuanLyPhongGym.core.mediator.IRequestHandler;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@Component
@Lazy
@RequiredArgsConstructor

public class GetListHuanLuyenVienQueryHandler implements IRequestHandler<GetListHuanLuyenVienQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListHuanLuyenVienQuery request) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    hlv.ID, " +
                        "    hlv.ID_KHACH_HANG, " +
                        "    hlv.ID_GOI_TAP, " +
                        "    hlv.ID_NGUOI_DUNG, " +
                        "    hlv.SO_DIEN_THOAI, " +
                        "    hlv.SO_DIEN_THOAI_HLV, " +
                        "    hlv.EMAIL_HLV, " +
                        "    hlv.VAI_TRO, " +
                        "    nd.TEN_NGUOI_DUNG, " +
                        "    gt.TEN_GOI_TAP, " +
                        "    kh.TEN_KHACH_HANG " +
                        "FROM HUAN_LUYEN_VIEN hlv " +
                        "JOIN NGUOI_DUNG nd ON hlv.ID_NGUOI_DUNG = nd.ID " +
                        "LEFT JOIN GOI_TAP gt ON hlv.ID_GOI_TAP = gt.ID " +
                        "LEFT JOIN KHACH_HANG kh ON hlv.ID_KHACH_HANG = kh.ID " +
                        "WHERE 1=1 ");

        StringBuilder countSql = new StringBuilder("""
                    SELECT COUNT(*)
                    FROM HUAN_LUYEN_VIEN hlv
                    WHERE 1=1
                """);

        List<Object> params = new ArrayList<>();
        List<Object> countParams = new ArrayList<>();

        boolean isFilter = false;

        if (request.getTenNguoiDung() != null && !request.getTenNguoiDung().isEmpty()) {
            isFilter = true;
            sql.append(" AND nd.TEN_NGUOI_DUNG LIKE ?");
            params.add("%" + request.getTenNguoiDung() + "%");
        }

        if (request.getSoDienThoai() != null && !request.getSoDienThoai().isEmpty()) {
            isFilter = true;
            sql.append(" AND hlv.SO_DIEN_THOAI = ?");
            params.add(request.getSoDienThoai());
        }

        if (request.getTenKhachHang() != null && !request.getTenKhachHang().isEmpty()) {
            isFilter = true;
            sql.append(" AND kh.TEN_KHACH_HANG LIKE ?");
            params.add("%" + request.getTenKhachHang() + "%");
        }

        if (request.getTenGoiTap() != null && !request.getTenGoiTap().isEmpty()) {
            isFilter = true;
            sql.append(" AND gt.TEN_GOI_TAP LIKE ?");
            params.add("%" + request.getTenGoiTap() + "%");
        }

        if (request.getVaiTro() != null) {
            isFilter = true;
            sql.append(" AND hlv.VAI_TRO = ?");
            params.add(request.getVaiTro());
        }

        int page = 1;
        int size = 5;
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
            sql.append(" ORDER BY gt.ID");
        }

        List<GetListHuanLuyenVienQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListHuanLuyenVienQueryDTO dto = new GetListHuanLuyenVienQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setIdKhachHang(rs.getString("ID_KHACH_HANG"));
                    dto.setIdNguoiDung(rs.getString("ID_NGUOI_DUNG"));
                    dto.setIdGoiTap(rs.getString("ID_GOI_TAP"));
                    dto.setTenGoiTap(rs.getString("TEN_GOI_TAP"));
                    dto.setTenNguoiDung(rs.getString("TEN_NGUOI_DUNG"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    dto.setSoDienThoaiHLV(rs.getString("SO_DIEN_THOAI_HLV"));
                    dto.setEmailHLV(rs.getString("EMAIL_HLV"));
                    dto.setVaiTro(rs.getInt("VAI_TRO"));
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
