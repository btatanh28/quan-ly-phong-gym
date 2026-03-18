package com.example.QuanLyPhongGym.core.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private SendGrid sendGrid;

    @Value("${email.from}")
    private String fromEmail;

    @Async
    public void sendVerificationEmail(String to, String code) {

        long start = System.currentTimeMillis();

        Email from = new Email(fromEmail);
        Email toEmail = new Email(to);

        String subject = "Mã xác nhận đăng ký";

        Content content = new Content(
                "text/html",
                "<h3>Mã xác nhận của bạn:</h3>" +
                        "<h1 style='color:blue'>" + code + "</h1>");

        Mail mail = new Mail(from, subject, toEmail, content);

        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            System.out.println("Status code: " + response.getStatusCode());
            System.out.println("SendGrid time: " + (System.currentTimeMillis() - start) + "ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
