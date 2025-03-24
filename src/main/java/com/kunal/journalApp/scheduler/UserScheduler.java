package com.kunal.journalApp.scheduler;

import com.kunal.journalApp.cache.AppCache;
import com.kunal.journalApp.constants.Sentiment;
import com.kunal.journalApp.models.JournalEntryModel;
import com.kunal.journalApp.models.UsersModel;
import com.kunal.journalApp.repository.UserRepositoryImpl;
import com.kunal.journalApp.service.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class UserScheduler {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;

    //    @Scheduled(cron = "0 0 9 ? * SUN")
    public void fetchUsersAndSendEmails() {

        List<UsersModel> allUsers = userRepository.getUsersBySA();

        for (UsersModel user : allUsers) {

            List<JournalEntryModel> journalEntries = user.getJournalEntries();

            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());

            Map<Sentiment, Integer> sentimentCount = new EnumMap<>(Sentiment.class);

            for (Sentiment sentiment : sentiments) {
                sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;
            for (Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if (mostFrequentSentiment != null) {
                emailSenderService.sendEmail(user.getEmail(), "Sentiment for last 7 days", "Your most frequent sentiment is " + mostFrequentSentiment.toString());
                log.info("Email send to {} for sentiment analysis for last 7 days", user.getEmail());
            }
        }


    }


    @Scheduled(cron = "0 0/10 * ? * *")
    public void resetAppCache() {
        appCache.init();
        log.info("App cache has been reset");

    }


}
