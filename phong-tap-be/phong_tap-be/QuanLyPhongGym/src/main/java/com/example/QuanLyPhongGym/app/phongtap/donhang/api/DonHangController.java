package com.example.QuanLyPhongGym.app.phongtap.donhang.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.donhang.command.create.CreateDonHangCommand;
import com.example.QuanLyPhongGym.app.phongtap.donhang.command.create.CreateDonHangCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.donhang.command.update.UpdateDonHangCommand;
import com.example.QuanLyPhongGym.app.phongtap.donhang.command.update.UpdateDonHangCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.donhang.query.get.GetDonHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.donhang.query.get.GetDonHangQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.donhang.query.get.GetDonHangQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.donhang.query.getkhachhang.GetListDonHangKhachHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.donhang.query.getkhachhang.GetListDonHangKhachHangQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.donhang.query.getlist.GetListDonHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.donhang.query.getlist.GetListDonHangQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("don-hang")
@RequiredArgsConstructor

public class DonHangController {
    private final CreateDonHangCommandHandler createDonHangCommandHandler;
    private final GetListDonHangQueryHandler getListDonHangQueryHandler;
    private final GetDonHangQueryHandler getDonHangQueryHandler;
    private final UpdateDonHangCommandHandler updateDonHangCommandHandler;
    private final GetListDonHangKhachHangQueryHandler getListDonHangKhachHangQueryHandler;

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListDonHangQuery request) {
        ListResponse response = getListDonHangQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("khach-hang/{id}")
    public ResponseEntity<ListResponse> getList(@PathVariable String id) {
        GetListDonHangKhachHangQuery request = new GetListDonHangKhachHangQuery();
        request.setIdKhachHang(id);

        ListResponse response = getListDonHangKhachHangQueryHandler.handle(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetDonHangQueryDTO> get(@PathVariable String id) {
        GetDonHangQuery request = new GetDonHangQuery();
        request.setId(id);
        GetDonHangQueryDTO response = getDonHangQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateDonHangCommand request) {
        DataResponse response = createDonHangCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("xac-nhan/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateDonHangCommand request) {
        DataResponse response = updateDonHangCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
