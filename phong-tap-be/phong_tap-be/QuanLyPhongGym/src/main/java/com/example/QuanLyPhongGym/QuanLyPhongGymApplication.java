package com.example.QuanLyPhongGym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync

public class QuanLyPhongGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuanLyPhongGymApplication.class, args);
    }

}
