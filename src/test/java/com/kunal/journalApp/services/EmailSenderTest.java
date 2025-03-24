package com.kunal.journalApp.services;

import com.kunal.journalApp.service.EmailSenderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailSenderTest {


    @Autowired
    private EmailSenderService emailSenderService;



    @Test
    public void sendEmail() {
        // send email

        String to = "kunalzyx2@gmail.com";
        String subject = "abc";
        String body = "sdfsdfsdfsd";

        Assertions.assertTrue(emailSenderService.sendEmail(to, subject, body));

    }


}
