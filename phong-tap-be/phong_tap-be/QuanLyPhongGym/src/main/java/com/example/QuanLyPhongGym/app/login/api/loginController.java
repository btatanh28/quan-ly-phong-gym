package com.example.QuanLyPhongGym.app.login.api;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.QuanLyPhongGym.app.login.command.ChangePasswordCommand;
import com.example.QuanLyPhongGym.app.login.command.ChangePasswordCommandHandler;
import com.example.QuanLyPhongGym.app.login.command.LoginCommand;
import com.example.QuanLyPhongGym.app.login.command.LoginCommandHandler;

import lombok.RequiredArgsConstructor;

@RestController
@Lazy
@RequestMapping("auth")
@RequiredArgsConstructor

public class loginController {
    private final LoginCommandHandler loginCommandHandler;
    private final ChangePasswordCommandHandler changePasswordCommandHandler;

    @PostMapping("login")
    public ResponseEntity<?> create(@RequestBody LoginCommand request) {
        return ResponseEntity.ok(loginCommandHandler.handle(request));
    }

    @PostMapping("new-password")
    public ResponseEntity<?> newPass(@RequestBody ChangePasswordCommand request) {
        return ResponseEntity.ok(changePasswordCommandHandler.handle(request));
    }
}
