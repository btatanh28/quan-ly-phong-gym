package com.example.QuanLyPhongGym.core.service.gemini.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

// import com.example.QuanLyPhongGym.core.service.gemini.GeminiService;
import com.example.QuanLyPhongGym.core.service.gemini.GroqService;
import com.example.QuanLyPhongGym.domain.entity.app.chitietdonhang.ChiTietDonHang;
import com.example.QuanLyPhongGym.domain.entity.app.dondangky.DonDangKy;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;
import com.example.QuanLyPhongGym.domain.repository.app.chitietdonhang.ChiTietDonHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.dondangky.DonDangKyRespository;
import com.example.QuanLyPhongGym.domain.repository.app.goitap.GoiTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.khachhang.KhachHangRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetap.TheTapRespository;
import com.example.QuanLyPhongGym.domain.repository.app.thetapgoitap.TheTapGoiTapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final GroqService groqService;

    private final KhachHangRespository khachHangRepository;

    private final GoiTapRespository goiTapRepository;

    private final TheTapRespository theTapRepository;

    private final TheTapGoiTapRepository theTapGoiTapRepository;

    private final ChiTietDonHangRespository chiTietDonHangRespository;

    private final DonDangKyRespository donDangKyRespository;

    public String chat(
            String id,
            String message) {

        String lower = message.toLowerCase();

        // Lấy danh sách gói tập từ database cho cả khách vãng lai và hội viên đã đăng nhập
        List<GoiTap> danhSachGoiTap = goiTapRepository.findAll();

        Map<String, GoiTap> danhSachGoiTapMap = danhSachGoiTap.stream()
                .collect(Collectors.toMap(
                        GoiTap::getId,
                        g -> g,
                        (g1, g2) -> g1
                ));

        String thongTinGoiTap = danhSachGoiTap.stream()
                .map(g -> "- " + g.getTenGoiTap()
                        + ": " + g.getGia() + " VNĐ")
                .collect(Collectors.joining("\n"));

        if (thongTinGoiTap.isBlank()) {
            thongTinGoiTap = "Hiện tại chưa có gói tập trong database.";
        }

        boolean needMemberInfo = lower.contains("gói")
                || lower.contains("thẻ")
                || lower.contains("đăng ký")
                || lower.contains("đã mua")
                || lower.contains("gia hạn")
                || lower.contains("hết hạn")
                || lower.contains("còn bao nhiêu ngày")
                || lower.contains("số ngày còn lại")
                || lower.contains("của tôi")
                || lower.contains("của mình")
                || lower.contains("hội viên")
                || lower.contains("tài khoản")
                || lower.contains("thời hạn");

        String thongTinThanhVien = "";

        if (id != null && !id.isBlank()) {

            // Lấy thông tin khách hàng từ cơ sở dữ liệu
            KhachHang khachHang = khachHangRepository
                    .findById(id)
                    .orElseThrow(
                            () -> new RuntimeException(
                                    "Không tìm thấy khách hàng"));

            if (needMemberInfo) {
                TheTap theTap = theTapRepository
                        .findFirstByIdKhachHangAndTrangThai(
                                khachHang.getId(),
                                1);

                String thongTinTheTap = "Khách hàng chưa có gói tập";

                // Nếu khách hàng có thẻ tập, lấy thông tin số ngày còn lại
                if (theTap != null) {
                    List<TheTapGoiTap> danhSachTheTapGoiTap = theTapGoiTapRepository
                            .findAllByIdTheTapAndTrangThai(
                                    theTap.getId(),
                                    1);

                    if (!danhSachTheTapGoiTap.isEmpty()) {
                        int tongSoNgayConLai = danhSachTheTapGoiTap.stream()
                                .mapToInt(TheTapGoiTap::getSoNgayConLai)
                                .sum();

                        thongTinTheTap = "- Số ngày còn lại: " + tongSoNgayConLai + " ngày";
                    }
                }

                List<DonDangKy> danhSachDonDangKy = donDangKyRespository
                        .findAllByIdKhachHang(khachHang.getId());

                String thongTinGoiTapDaMua = "Khách hàng chưa mua gói tập";

                if (!danhSachDonDangKy.isEmpty()) {
                    List<String> idsDonHang = danhSachDonDangKy.stream()
                            .map(DonDangKy::getId)
                            .toList();

                    List<ChiTietDonHang> danhSachChiTiet = chiTietDonHangRespository
                            .findAllByIdDonHangIn(idsDonHang);

                    if (!danhSachChiTiet.isEmpty()) {

                        thongTinGoiTapDaMua = danhSachChiTiet.stream()
                                .map(ct -> {

                                    GoiTap goiTap = danhSachGoiTapMap.get(ct.getIdGoiTap());

                                    if (goiTap == null) {
                                        return null;
                                    }

                                    return "- "
                                            + goiTap.getTenGoiTap()
                                            + ": "
                                            + goiTap.getGia()
                                            + " VNĐ";

                                })
                                .filter(item -> item != null)
                                .collect(Collectors.joining("\n"));
                    }
                }

                thongTinThanhVien = """
                        Thông tin khách hàng:
                        - Họ tên: %s
                        - Email: %s
                        - Số điện thoại: %s
                        
                        Thông tin gói tập hiện tại của khách:
                        %s
                        
                        Danh sách gói tập:
                        %s
                        
                        Các gói tập đã mua:
                        %s
                        """
                        .formatted(
                                khachHang.getTenKhachHang(),
                                khachHang.getEmail(),
                                khachHang.getSoDienThoai(),
                                thongTinTheTap,
                                thongTinGoiTap,
                                thongTinGoiTapDaMua);
            }
        }

        String doiTuong;

        if (id == null || id.isBlank()) {
            doiTuong = """
                    Người dùng hiện tại là KHÁCH VÃNG LAI.
                    Người dùng chưa đăng nhập.
                    Chỉ tư vấn thông tin công khai của phòng gym.
                    Không được giả định người dùng đã có tài khoản hoặc thẻ tập.
                    Không được tự tạo thông tin cá nhân, đơn hàng hoặc lịch sử đăng ký.
                    Nếu khách hỏi thông tin cá nhân, hãy thông báo cần đăng nhập để tra cứu.
                    """;
        } else {
            doiTuong = """
                    Người dùng hiện tại là HỘI VIÊN ĐÃ ĐĂNG NHẬP.
                    Có thể sử dụng thông tin hội viên được cung cấp bên dưới
                    để trả lời câu hỏi liên quan đến tài khoản và thẻ tập.
                    Chỉ sử dụng dữ liệu thực tế được cung cấp.
                    Không tự suy đoán thông tin cá nhân.
                    """;
        }

        String context = """
                Bạn là chatbot tư vấn chính thức của phòng gym Blue Gym.
                
                ĐỐI TƯỢNG NGƯỜI DÙNG:
                %s
                
                Câu hỏi:
                %s
                
                %s
                
                Nhiệm vụ:
                - Trả lời đúng trọng tâm câu hỏi.
                - Tư vấn về gói tập, huấn luyện viên, lịch tập.
                - Tư vấn dinh dưỡng, chế độ ăn và tập luyện.
                - Hỗ trợ các mục tiêu: tăng cân, giảm cân, tăng cơ, giảm mỡ, giữ dáng và cải thiện sức khỏe.
                
                
                Quy tắc bắt buộc:
                - Chỉ trả lời tối đa 4 câu hoặc 6 dòng.
                - Không viết đoạn văn dài.
                - Không giải thích lan man.
                - Dễ hiểu, thân thiện, có thể dùng emoji.
                - Không dùng markdown (*, **, #).
                - Sau khi trả lời xong, không gợi ý thêm các chủ đề khác.
                - Không hỏi "Bạn có muốn..." hoặc "Bạn cần tư vấn..." nếu người dùng không yêu cầu.
                - Chỉ trả lời đúng nội dung người dùng hỏi.
                - Không giới thiệu gói tập nếu khách không hỏi.
                - Chỉ hỏi lại khi câu hỏi hiện tại thật sự thiếu thông tin.
                - Nếu người dùng đã nói rõ mục tiêu ngay trong câu hỏi thì không hỏi lại.
                - Không chẩn đoán bệnh; nếu có vấn đề sức khỏe hãy khuyên khách tham khảo bác sĩ.
                - Địa chỉ chính thức của Blue Gym: P. Võ Thị Sáu / 101 P. Thanh Nhàn, Hai Bà Trưng, Hà Nội 10000, Việt Nam.
                - Số điện thoại chính thức: 0918492841.
                - Khi khách hỏi địa chỉ hoặc số điện thoại, chỉ được sử dụng thông tin trên.
                - Tuyệt đối không tự tạo hoặc suy đoán địa chỉ, số điện thoại.
                """
                .formatted(
                        doiTuong,
                        message,
                        thongTinGoiTap,
                        thongTinThanhVien);

        return groqService.chat(context);

    }
}
