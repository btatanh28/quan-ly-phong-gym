package com.example.QuanLyPhongGym.app.phongtap.sanphamgym.api;

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

import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.create.CreateSanPhamGymCommand;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.create.CreateSanPhamGymCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.delete.DeleteSanPhamGymCommand;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.delete.DeleteSanPhamGymCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.update.UpdateSanPhamGymCommand;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.command.update.UpdateSanPhamGymCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.get.GetSanPhamGymQuery;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.get.GetSanPhamGymQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.get.GetSanPhamGymQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getComboBox.GetSanPhamGymComboboxQuery;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getComboBox.GetSanPhamGymComboboxQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getlist.GetListSanPhamGymQuery;
import com.example.QuanLyPhongGym.app.phongtap.sanphamgym.query.getlist.GetListSanPhamGymQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("san-pham-gym")
@RequiredArgsConstructor

public class SanPhamGymController {
    private final CreateSanPhamGymCommandHandler createSanPhamGymCommandHandler;
    private final GetListSanPhamGymQueryHandler getListSanPhamGymQueryHandler;
    private final GetSanPhamGymComboboxQueryHandler getSanPhamGymComboboxQueryHandler;
    private final GetSanPhamGymQueryHandler getSanPhamGymQueryHandler;
    private final UpdateSanPhamGymCommandHandler updateSanPhamGymCommandHandler;
    private final DeleteSanPhamGymCommandHandler deleteSanPhamGymCommandHandler;

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateSanPhamGymCommand request) {
        DataResponse response = createSanPhamGymCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateSanPhamGymCommand request) {
        DataResponse response = updateSanPhamGymCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteSanPhamGymCommand request) {
        DataResponse response = deleteSanPhamGymCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListSanPhamGymQuery request) {
        ListResponse response = getListSanPhamGymQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<GetSanPhamGymQueryDTO> get(@PathVariable String id) {
        GetSanPhamGymQuery request = new GetSanPhamGymQuery(); // để handler nhận
        request.setId(id);
        GetSanPhamGymQueryDTO response = getSanPhamGymQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("combobox")
    public ResponseEntity<ListResponse> combobox(@ModelAttribute GetSanPhamGymComboboxQuery request) {

        ListResponse response = getSanPhamGymComboboxQueryHandler.handle(request);

        return ResponseEntity.ok(response);
    }
}
