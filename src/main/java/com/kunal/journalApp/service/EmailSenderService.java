package com.kunal.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendEmail(String to, String subject, String body) {
        // send email

        try {
            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(to);
            mail.setSubject(subject);
            mail.setText(body);

            javaMailSender.send(mail);
        } catch (MailException e) {
           log.error("Error while sending email to {} : ", to, e);
            return false;
        }

        return true;
    }

}
