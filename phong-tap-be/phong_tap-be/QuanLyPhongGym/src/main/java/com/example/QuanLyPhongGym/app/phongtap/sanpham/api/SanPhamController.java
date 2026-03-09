package com.example.QuanLyPhongGym.app.phongtap.sanpham.api;

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

import com.example.QuanLyPhongGym.app.phongtap.sanpham.command.create.CreateSanPhamCommand;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.command.create.CreateSanPhamCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.command.delete.DeleteSanPhamCommand;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.command.delete.DeleteSanPhamCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.command.update.UpdateSanPhamCommand;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.command.update.UpdateSanPhamCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.query.get.GetSanPhamQuery;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.query.get.GetSanPhamQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.query.get.GetSanPhamQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getComboBox.GetSanPhamComboboxQuery;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getComboBox.GetSanPhamComboboxQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getlist.GetListSanPhamQuery;
import com.example.QuanLyPhongGym.app.phongtap.sanpham.query.getlist.GetListSanPhamQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("san-pham")
@RequiredArgsConstructor

public class SanPhamController {
    private final CreateSanPhamCommandHandler createSanPhamCommandHandler;
    private final UpdateSanPhamCommandHandler updateSanPhamCommandHandler;
    private final GetListSanPhamQueryHandler getListSanPhamQueryHandler;
    private final GetSanPhamQueryHandler getSanPhamQueryHandler;
    private final DeleteSanPhamCommandHandler deleteSanPhamCommandHandler;
    private final GetSanPhamComboboxQueryHandler getSanPhamComboboxQueryHandler;
    // private final KiemTraTonKhoQueryHandler kiemTraTonKhoQueryHandler;

    @GetMapping("{id}")
    public ResponseEntity<GetSanPhamQueryDTO> get(@PathVariable String id) {
        GetSanPhamQuery request = new GetSanPhamQuery(); // để handler nhận
        request.setId(id);
        GetSanPhamQueryDTO response = getSanPhamQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListSanPhamQuery request) {
        ListResponse response = getListSanPhamQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    // @GetMapping("tonkho/{id}")
    // public ResponseEntity<KiemTraTonKhoQueryDTO> kiemTraTonKho(@PathVariable
    // String id) {
    // KiemTraTonKhoQuery request = new KiemTraTonKhoQuery();
    // request.setId(id);
    // KiemTraTonKhoQueryDTO response = kiemTraTonKhoQueryHandler.handle(request);
    // return ResponseEntity.ok(response);
    // }

    @PostMapping()
    public ResponseEntity<DataResponse> create(@RequestBody CreateSanPhamCommand request) {
        DataResponse response = createSanPhamCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateSanPhamCommand request) {
        DataResponse response = updateSanPhamCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteSanPhamCommand request) {
        DataResponse response = deleteSanPhamCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("combobox")
    public ResponseEntity<ListResponse> combobox(@ModelAttribute GetSanPhamComboboxQuery request) {

        ListResponse response = getSanPhamComboboxQueryHandler.handle(request);

        return ResponseEntity.ok(response);
    }
}
