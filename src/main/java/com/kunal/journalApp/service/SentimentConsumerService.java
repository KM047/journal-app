package com.kunal.journalApp.service;

import com.kunal.journalApp.models.SentimentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SentimentConsumerService {

    @Autowired
    private EmailSenderService emailService;

    @KafkaListener(topics = "default_topic", groupId = "groupid")
    public void consume(SentimentData sentimentData) {
        sendEmail(sentimentData);
        log.info("Email sent to {}", sentimentData.getEmail());
    }

    private void sendEmail(SentimentData sentimentData) {
        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
    }
}