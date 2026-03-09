package com.example.QuanLyPhongGym.app.phongtap.donhang.query.getkhachhang;

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

public class GetListDonHangKhachHangQueryHandler
        implements IRequestHandler<GetListDonHangKhachHangQuery, ListResponse> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public ListResponse handle(GetListDonHangKhachHangQuery request) {
        StringBuilder sql = new StringBuilder(
                "SELECT " +
                        "    dh.ID, " +
                        "    dh.ID_KHACH_HANG, " +
                        "    kh.TEN_KHACH_HANG, " +
                        "    dh.SO_DIEN_THOAI, " +
                        "    dh.EMAIL, " +
                        "    dh.TONG_TIEN, " +
                        "    dh.TRANG_THAI_SAN_PHAM, " +
                        "    dh.HINH_THUC_THANH_TOAN, " +
                        "    dh.NGAY_MUA " +
                        "FROM DON_DANG_KY dh " +
                        "JOIN KHACH_HANG kh " +
                        "    ON dh.ID_KHACH_HANG = kh.ID " +
                        "WHERE 1=1 ");

        // StringBuilder countSql = new StringBuilder("""
        // SELECT COUNT(*)
        // FROM SAN_PHAM sp
        // JOIN DANH_MUC_SAN_PHAM dm ON sp.ID_DANH_MUC = dm.ID
        // WHERE 1=1
        // """);

        List<Object> params = new ArrayList<>();
        // List<Object> countParams = new ArrayList<>();

        // boolean isFilter = false;

        if (request.getIdKhachHang() != null) {
            sql.append(" AND dh.ID_KHACH_HANG = ? ");
            params.add(request.getIdKhachHang());
        }

        sql.append(" ORDER BY dh.NGAY_MUA DESC");

        // int page = 1;
        // int size = 9;
        // int offset = 0;

        // if (!isFilter) {
        // page = request.getPage() != null ? request.getPage() : 0;
        // size = request.getSize() != null ? request.getSize() : request.getSize();
        // offset = page * size;
        // sql.append(" ORDER BY sp.ID DESC LIMIT ? OFFSET ?");
        // params.add(size);
        // params.add(offset);
        // } else {
        // // Nếu có filter, không phân trang
        // sql.append(" ORDER BY sp.ID DESC");
        // }

        List<GetListDonHangKhachHangQueryDTO> items = jdbcTemplate.query(
                sql.toString(),
                params.toArray(),
                (rs, rowNum) -> {
                    GetListDonHangKhachHangQueryDTO dto = new GetListDonHangKhachHangQueryDTO();
                    dto.setId(rs.getString("ID"));
                    dto.setIdKhachHang(rs.getString("ID_KHACH_HANG"));
                    dto.setTenKhachHang(rs.getString("TEN_KHACH_HANG"));
                    dto.setSoDienThoai(rs.getString("SO_DIEN_THOAI"));
                    dto.setEmail(rs.getString("EMAIL"));
                    dto.setTongTien(rs.getBigDecimal("TONG_TIEN"));
                    dto.setNgayMua(rs.getLong("NGAY_MUA"));
                    dto.setHinhThucThanhToan(rs.getInt("HINH_THUC_THANH_TOAN"));
                    dto.setTrangThaiSanPham(rs.getInt("TRANG_THAI_SAN_PHAM"));

                    if (dto.getHinhThucThanhToan() == 1) {
                        dto.setHinhThucThanhToanLabel("Thanh toán tiền mặt");
                    }

                    if (dto.getHinhThucThanhToan() == 2) {
                        dto.setHinhThucThanhToanLabel("Thanh toán chuyển khoản");
                    }

                    if (dto.getTrangThaiSanPham() == 1) {
                        dto.setTrangThaiSanPhamLabel("Đang Xử Lý");
                    }
                    if (dto.getTrangThaiSanPham() == 2) {
                        dto.setTrangThaiSanPhamLabel("Đã thanh toán");
                    }
                    if (dto.getTrangThaiSanPham() == 3) {
                        dto.setTrangThaiSanPhamLabel("Đăng ký thành công");
                    }

                    return dto;
                });

        // Long totalItems;

        // if (isFilter) {
        // totalItems = (long) items.size();
        // } else {
        // totalItems = jdbcTemplate.queryForObject(
        // countSql.toString(),
        // countParams.toArray(),
        // Long.class);
        // }

        // Bao bọc thành ListResponse
        ListResponse response = new ListResponse();
        response.setItems(items);
        // response.setTotalItems(totalItems);
        // response.setPage(page);
        // response.setSize(size);
        // response.setTotalPages((int) Math.ceil((double) totalItems / size));

        return response;
    }
}
