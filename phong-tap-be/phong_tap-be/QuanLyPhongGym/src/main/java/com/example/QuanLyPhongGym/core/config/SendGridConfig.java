package com.example.QuanLyPhongGym.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.sendgrid.SendGrid;

@Configuration
public class SendGridConfig {
    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(apiKey);
    }
}
