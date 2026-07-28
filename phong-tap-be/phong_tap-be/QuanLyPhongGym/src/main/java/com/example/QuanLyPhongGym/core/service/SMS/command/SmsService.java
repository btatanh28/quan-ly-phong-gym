// package com.example.QuanLyPhongGym.core.service.SMS.command;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// @Service
// public class SmsService {

//     @Value("${esms.api-key}")
//     private String apiKey;

//     @Value("${esms.secret-key}")
//     private String secretKey;

//     @Value("${esms.url}")
//     private String url;

//     public String sendOtp(String phone, String otp) {

//         // Chuyển 09xxxxxxxx -> 849xxxxxxxx
//         if (phone.startsWith("0")) {
//             phone = "84" + phone.substring(1);
//         }

//         RestTemplate restTemplate = new RestTemplate();

//         HttpHeaders headers = new HttpHeaders();
//         headers.setContentType(MediaType.APPLICATION_JSON);

//         Map<String, Object> body = new HashMap<>();
//         body.put("ApiKey", apiKey);
//         body.put("SecretKey", secretKey);
//         body.put("Phone", phone);
//         body.put("Content", "Ma xac thuc cua ban la: " + otp + ". Ma co hieu luc trong 5 phut.");
//         body.put("SmsType", 2); // OTP

//         HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

//         try {

//             String response = restTemplate.postForObject(
//                     url,
//                     entity,
//                     String.class);

//             System.out.println("=========== eSMS Response ===========");
//             System.out.println(response);
//             System.out.println("=====================================");

//             return response;

//         } catch (Exception ex) {

//             ex.printStackTrace();

//             throw new RuntimeException("Gửi OTP thất bại: " + ex.getMessage());

//         }
//     }

// }