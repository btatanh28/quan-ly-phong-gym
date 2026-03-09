package com.example.QuanLyPhongGym.app.phongtap.danhgia.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.phongtap.danhgia.command.create.CreateDanhGiaCommand;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.command.create.CreateDanhGiaCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.command.delete.DeleteDanhGiaCommand;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.command.delete.DeleteDanhGiaCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.command.update.UpdateDanhGiaCommand;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.command.update.UpdateDanhGiaCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.query.get.GetDanhGiaQuery;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.query.get.GetDanhGiaQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.query.get.GetDanhGiaQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.query.getDanhGiaKhachHang.GetListDanhGiaKhachHangQuery;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.query.getDanhGiaKhachHang.GetListDanhGiaKhachHangQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.query.getlist.GetListDanhGiaQuery;
import com.example.QuanLyPhongGym.app.phongtap.danhgia.query.getlist.GetListDanhGiaQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("danh-gia")
@RequiredArgsConstructor

public class DanhGiaController {
    private final GetListDanhGiaQueryHandler getListDanhGiaQueryHandler;
    private final GetDanhGiaQueryHandler getDanhGiaQueryHandler;
    private final CreateDanhGiaCommandHandler createDanhGiaCommandHandler;
    private final UpdateDanhGiaCommandHandler updateDanhGiaCommandHandler;
    private final DeleteDanhGiaCommandHandler deleteDanhGiaCommandHandler;
    private final GetListDanhGiaKhachHangQueryHandler getListDanhGiaKhachHangQueryHandler;

    @GetMapping("{id}")
    public ResponseEntity<GetDanhGiaQueryDTO> get(@PathVariable String id) {
        GetDanhGiaQuery request = new GetDanhGiaQuery(); // để handler nhận
        request.setId(id);
        GetDanhGiaQueryDTO response = getDanhGiaQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("khach-hang/{id}")
    public ResponseEntity<ListResponse> getList(@PathVariable String id) {
        GetListDanhGiaKhachHangQuery request = new GetListDanhGiaKhachHangQuery();
        request.setIdKhachHang(id);

        ListResponse response = getListDanhGiaKhachHangQueryHandler.handle(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListDanhGiaQuery request) {
        ListResponse response = getListDanhGiaQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateDanhGiaCommand request) {
        DataResponse response = createDanhGiaCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateDanhGiaCommand request) {
        DataResponse response = updateDanhGiaCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteDanhGiaCommand request) {
        DataResponse response = deleteDanhGiaCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
