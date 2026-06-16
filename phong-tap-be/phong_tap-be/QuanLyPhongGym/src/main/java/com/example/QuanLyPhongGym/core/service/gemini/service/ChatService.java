package com.example.QuanLyPhongGym.core.service.gemini.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

// import com.example.QuanLyPhongGym.core.service.gemini.GeminiService;
import com.example.QuanLyPhongGym.core.service.gemini.GroqService;
import com.example.QuanLyPhongGym.domain.entity.app.goitap.GoiTap;
import com.example.QuanLyPhongGym.domain.entity.app.khachhang.KhachHang;
import com.example.QuanLyPhongGym.domain.entity.app.thetap.TheTap;
import com.example.QuanLyPhongGym.domain.entity.app.thetapgoitap.TheTapGoiTap;
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

        public String chat(
                        String id,
                        String message) {

                KhachHang khachHang = khachHangRepository
                                .findById(id)
                                .orElseThrow(
                                                () -> new RuntimeException(
                                                                "Không tìm thấy khách hàng"));

                List<GoiTap> danhSachGoiTap = goiTapRepository.findAll();

                TheTap theTap = theTapRepository
                                .findFirstByIdKhachHangAndTrangThai(
                                                khachHang.getId(),
                                                1);

                String thongTinGoiTap = danhSachGoiTap.stream()
                                .map(g -> "- " + g.getTenGoiTap()
                                                + ": "
                                                + g.getGia()
                                                + " VNĐ")
                                .collect(Collectors.joining("\n"));

                String thongTinTheTap = "Khách hàng chưa có gói tập";

                if (theTap != null) {
                        List<TheTapGoiTap> danhSachTheTapGoiTap = theTapGoiTapRepository
                                        .findAllByIdTheTapAndTrangThai(
                                                        theTap.getId(),
                                                        1);

                        if (!danhSachTheTapGoiTap.isEmpty()) {

                                int tongSoNgayConLai = danhSachTheTapGoiTap.stream()
                                                .mapToInt(TheTapGoiTap::getSoNgayConLai)
                                                .sum();

                                thongTinTheTap = """

                                                Gói tập hiện tại:

                                                - Tổng số ngày còn lại: %s ngày

                                                """
                                                .formatted(
                                                                tongSoNgayConLai);
                        }
                }

                String context = """

                                Bạn là chatbot chăm sóc khách hàng của phòng gym.

                                Thông tin khách hàng:

                                - Họ tên: %s
                                - Email: %s
                                - Số điện thoại: %s

                                Danh sách gói tập hiện có:
                                %s


                                Thông tin gói tập khách hàng:
                                %s


                                Nhiệm vụ:
                                - Tư vấn các dịch vụ của phòng gym.
                                - Hỗ trợ khách hàng về gói tập, lịch tập, huấn luyện viên.
                                - Trả lời thân thiện như nhân viên tư vấn thật.


                                Quy tắc trả lời:
                                - Trả lời ngắn gọn, dễ đọc trên ứng dụng chat.
                                - Không dùng ký hiệu markdown như *, **, #.
                                - Có thể sử dụng emoji phù hợp.
                                - Không trả lời quá 7 dòng.
                                - Nếu khách hỏi giới thiệu phòng gym, trả lời theo mẫu:


                                Xin chào anh/chị %s 👋

                                💪 Phòng gym bên em có:
                                • Không gian tập luyện hiện đại, sạch sẽ.
                                • Trang thiết bị đầy đủ, phù hợp nhiều mục tiêu.
                                • Các lớp Yoga, Zumba, Group X.
                                • Huấn luyện viên hỗ trợ tận tình.

                                🎁 Hiện phòng gym đang có chương trình trải nghiệm miễn phí.
                                Anh/chị muốn đăng ký lịch tập thử vào thời gian nào ạ?


                                Câu hỏi của khách hàng:
                                %s


                                """
                                .formatted(

                                                khachHang.getTenKhachHang(),

                                                khachHang.getEmail(),

                                                khachHang.getSoDienThoai(),

                                                thongTinGoiTap,

                                                thongTinTheTap,

                                                khachHang.getTenKhachHang(),

                                                message

                                );

                return groqService.chat(context);

        }

}