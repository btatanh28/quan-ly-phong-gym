package com.example.QuanLyPhongGym.domain.repository.app.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.QuanLyPhongGym.domain.entity.app.user.User;

public interface UserRespository extends JpaRepository<User, String> {
    boolean existsByEmail(String email);

    boolean existsBySoDienThoai(String soDienThoai);

    User findFirstById(String id);

    User findFirstByEmail(String email);
}
