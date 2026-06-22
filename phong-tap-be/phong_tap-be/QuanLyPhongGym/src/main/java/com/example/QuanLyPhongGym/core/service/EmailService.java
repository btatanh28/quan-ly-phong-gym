package com.example.QuanLyPhongGym.core.service;

// import com.sendgrid.*;
// import com.sendgrid.helpers.mail.Mail;
// import com.sendgrid.helpers.mail.objects.Content;
// import com.sendgrid.helpers.mail.objects.Email;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.scheduling.annotation.Async;
// import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

// @Service
// public class EmailService {
//     @Autowired
//     private SendGrid sendGrid;

//     @Value("${email.from}")
//     private String fromEmail;

//     @Async
//     public void sendVerificationEmail(String to, String code) {

//         long start = System.currentTimeMillis();

//         Email from = new Email(fromEmail);
//         Email toEmail = new Email(to);

//         String subject = "Mã xác nhận đăng ký";

//         Content content = new Content(
//                 "text/html",
//                 "<h3>Mã xác nhận của bạn:</h3>" +
//                         "<h1 style='color:blue'>" + code + "</h1>");

//         Mail mail = new Mail(from, subject, toEmail, content);

//         Request request = new Request();

//         try {
//             request.setMethod(Method.POST);
//             request.setEndpoint("mail/send");
//             request.setBody(mail.build());

//             Response response = sendGrid.api(request);

//             System.out.println("Status code: " + response.getStatusCode());
//             System.out.println("SendGrid time: " + (System.currentTimeMillis() - start) + "ms");

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(String to, String code) {

        long start = System.currentTimeMillis();

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    true,
                    "UTF-8");

            helper.setFrom(
                    "tuananh28tuananh28@gmail.com");

            helper.setTo(to);

            helper.setSubject(
                    "Mã xác nhận đăng ký");

            String content = "<h3>Mã xác nhận của bạn:</h3>" +
                    "<h1 style='color:blue'>" +
                    code +
                    "</h1>";

            helper.setText(
                    content,
                    true);

            mailSender.send(message);

            System.out.println(
                    "Gmail send time: "
                            + (System.currentTimeMillis() - start)
                            + "ms");

        } catch (MessagingException e) {

            e.printStackTrace();

        }
    }
}
