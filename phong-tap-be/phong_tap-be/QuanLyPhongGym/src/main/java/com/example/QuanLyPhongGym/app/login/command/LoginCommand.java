package com.example.QuanLyPhongGym.app.login.command;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class LoginCommand {
    private String userAccount;
    private String matKhau;
}
