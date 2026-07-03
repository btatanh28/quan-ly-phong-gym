package com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.api;

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

import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.create.CreateHuanLuyenVienCommand;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.create.CreateHuanLuyenVienCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.delete.DeleteHuanLuyenVienCommand;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.delete.DeleteHuanLuyenVienCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.update.UpdateHuanLuyenVienCommand;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.command.update.UpdateHuanLuyenVienCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.get.GetHuanLuyenVienQuery;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.get.GetHuanLuyenVienQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.get.GetHuanLuyenVienQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.getList.GetListHuanLuyenVienQuery;
import com.example.QuanLyPhongGym.app.phongtap.huanluyenvien.query.getList.GetListHuanLuyenVienQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("huan-luyen-vien")
@RequiredArgsConstructor

public class HuanLuyenVienController {
    private final CreateHuanLuyenVienCommandHandler createHuanLuyenVienCommandHandler;
    private final GetListHuanLuyenVienQueryHandler getListHuanLuyenVienQueryHandler;
    private final GetHuanLuyenVienQueryHandler getHuanLuyenVienQueryHandler;
    private final UpdateHuanLuyenVienCommandHandler updateHuanLuyenVienCommandHandler;
    private final DeleteHuanLuyenVienCommandHandler deleteHuanLuyenVienCommandHandler;

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListHuanLuyenVienQuery request) {
        ListResponse response = getListHuanLuyenVienQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetHuanLuyenVienQueryDTO> get(@PathVariable String id) {
        GetHuanLuyenVienQuery request = new GetHuanLuyenVienQuery(); // để handler nhận
        request.setId(id);
        GetHuanLuyenVienQueryDTO response = getHuanLuyenVienQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<DataResponse> create(@RequestBody CreateHuanLuyenVienCommand request) {
        DataResponse response = createHuanLuyenVienCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateHuanLuyenVienCommand request) {
        DataResponse response = updateHuanLuyenVienCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteHuanLuyenVienCommand request) {
        DataResponse response = deleteHuanLuyenVienCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
