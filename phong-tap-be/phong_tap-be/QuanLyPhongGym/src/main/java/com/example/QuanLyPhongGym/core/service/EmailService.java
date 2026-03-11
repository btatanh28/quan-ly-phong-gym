package com.example.QuanLyPhongGym.core.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${email.from}")
    private String fromEmail;

    public void sendVerificationEmail(String to, String code) {

        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);

        String subject = "Mã xác nhận đăng ký";

        Content content = new Content(
                "text/plain",
                "Mã xác nhận của bạn là: " + code);

        Mail mail = new Mail(from, subject, toEmail, content);

        SendGrid sg = new SendGrid(apiKey);

        Request request = new Request();

        try {

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("Status code: " + response.getStatusCode());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
