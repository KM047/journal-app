package com.kunal.journalApp.cache;


import com.kunal.journalApp.models.ConfigJournalAppModel;
import com.kunal.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Data
public class AppCache {

    public enum keys {
        WEATHER_API
    }

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    private Map<String, String > appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppModel> configJournalAppModels = configJournalAppRepository.findAll();

        for (ConfigJournalAppModel config : configJournalAppModels) {
            appCache.put(config.getKey(), config.getValue());
        }


    }

}
