package com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.api;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.command.create.CreateChiTietDonHangCommand;
import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.command.create.CreateChiTietDonHangCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.get.GetChiTietDonHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.get.GetChiTietDonHangQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.chitietdonhang.query.get.GetChiTietDonHangQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("chi-tiet-don-hang")
@RequiredArgsConstructor

public class ChiTietDonHangController {
    private final CreateChiTietDonHangCommandHandler createChiTietDonHangCommandHandler;
    private final GetChiTietDonHangQueryHandler getChiTietDonHangQueryHandler;

    @GetMapping("{id}")
    public ResponseEntity<List<GetChiTietDonHangQueryDTO>> get(
            @PathVariable String id) {

        GetChiTietDonHangQuery request = new GetChiTietDonHangQuery();
        request.setId(id);

        return ResponseEntity.ok(
                getChiTietDonHangQueryHandler.handle(request));
    }

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateChiTietDonHangCommand request) {
        DataResponse response = createChiTietDonHangCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
