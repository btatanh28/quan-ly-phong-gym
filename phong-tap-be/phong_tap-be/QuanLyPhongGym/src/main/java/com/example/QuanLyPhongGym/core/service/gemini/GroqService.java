// package com.example.QuanLyPhongGym.core.service.gemini;

// import java.util.List;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

// @Service
// public class GeminiService {

//     @Value("${gemini.api-key}")
//     private String apiKey;

//     private final RestTemplate restTemplate = new RestTemplate();

//     public String chat(String message) {

//         String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key="
//                 + apiKey;

//         Map<String, Object> body = Map.of(
//                 "contents",
//                 List.of(
//                         Map.of(
//                                 "parts",
//                                 List.of(
//                                         Map.of(
//                                                 "text",
//                                                 message)))));

//         HttpHeaders headers = new HttpHeaders();

//         headers.setContentType(
//                 MediaType.APPLICATION_JSON);

//         HttpEntity<Map<String, Object>> request = new HttpEntity<>(
//                 body,
//                 headers);

//         ResponseEntity<Map> response = restTemplate.postForEntity(
//                 url,
//                 request,
//                 Map.class);

//         Map result = response.getBody();

//         if (result == null) {
//             return "Không nhận được phản hồi từ Gemini";
//         }

//         List candidates = (List) result.get("candidates");

//         if (candidates == null || candidates.isEmpty()) {
//             return "Gemini không có câu trả lời";
//         }

//         Map candidate = (Map) candidates.get(0);

//         Map content = (Map) candidate.get("content");

//         List parts = (List) content.get("parts");

//         Map part = (Map) parts.get(0);

//         return part.get("text").toString();

//     }
// }

package com.example.QuanLyPhongGym.core.service.gemini;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GroqService {

        @Value("${groq.api-key}")
        private String apiKey;

        private final RestTemplate restTemplate = new RestTemplate();

        public String chat(String message) {

                String url = "https://api.groq.com/openai/v1/chat/completions";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(apiKey);

                Map<String, Object> body = Map.of(
                                "model", "llama-3.1-8b-instant",
                                "messages", List.of(
                                                Map.of(
                                                                "role", "user",
                                                                "content", message)),
                                "temperature", 0.7);

                HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

                ResponseEntity<Map> response = restTemplate.postForEntity(
                                url,
                                request,
                                Map.class);

                Map result = response.getBody();

                if (result == null) {
                        return "Không nhận được phản hồi từ Groq";
                }

                List choices = (List) result.get("choices");

                if (choices == null || choices.isEmpty()) {
                        return "Groq không có câu trả lời";
                }

                Map choice = (Map) choices.get(0);
                Map messageObj = (Map) choice.get("message");

                return messageObj.get("content").toString();
        }
}