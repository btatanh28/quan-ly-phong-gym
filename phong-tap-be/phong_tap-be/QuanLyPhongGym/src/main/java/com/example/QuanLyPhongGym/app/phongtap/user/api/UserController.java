package com.example.QuanLyPhongGym.app.phongtap.user.api;

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

import com.example.QuanLyPhongGym.app.phongtap.user.command.create.CreateUserCommand;
import com.example.QuanLyPhongGym.app.phongtap.user.command.create.CreateUserCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.user.command.delete.DeleteUserCommand;
import com.example.QuanLyPhongGym.app.phongtap.user.command.delete.DeleteUserCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.user.command.update.UpdateUserCommand;
import com.example.QuanLyPhongGym.app.phongtap.user.command.update.UpdateUserCommandHandler;
import com.example.QuanLyPhongGym.app.phongtap.user.query.get.GetUserQuery;
import com.example.QuanLyPhongGym.app.phongtap.user.query.get.GetUserQueryDTO;
import com.example.QuanLyPhongGym.app.phongtap.user.query.get.GetUserQueryHandler;
import com.example.QuanLyPhongGym.app.phongtap.user.query.getlist.GetListUserQuery;
import com.example.QuanLyPhongGym.app.phongtap.user.query.getlist.GetListUserQueryHandler;
import com.example.QuanLyPhongGym.core.model.response.DataResponse;
import com.example.QuanLyPhongGym.core.model.response.ListResponse;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("user")
@RequiredArgsConstructor

public class UserController {
    private final GetUserQueryHandler getUserQueryHandler;
    private final GetListUserQueryHandler getListUserQueryHandler;
    private final CreateUserCommandHandler createUserCommandHandler;
    private final UpdateUserCommandHandler updateUserCommandHandler;
    private final DeleteUserCommandHandler deleteUserCommandHandler;

    @GetMapping("{id}")
    public ResponseEntity<GetUserQueryDTO> get(@PathVariable String id) {
        GetUserQuery request = new GetUserQuery(); // để handler nhận
        request.setId(id);
        GetUserQueryDTO response = getUserQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("list")
    public ResponseEntity<ListResponse> getList(@ModelAttribute GetListUserQuery request) {
        ListResponse response = getListUserQueryHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity<DataResponse> create(@RequestBody CreateUserCommand request) {
        DataResponse response = createUserCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<DataResponse> update(@RequestBody UpdateUserCommand request) {
        DataResponse response = updateUserCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<DataResponse> delete(DeleteUserCommand request) {
        DataResponse response = deleteUserCommandHandler.handle(request);
        return ResponseEntity.ok(response);
    }
}
