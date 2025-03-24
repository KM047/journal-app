package com.kunal.journalApp.service;

import com.kunal.journalApp.models.JournalEntryModel;
import com.kunal.journalApp.models.UsersModel;
import com.kunal.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    @Transactional
    public void saveEntry(JournalEntryModel journalEntryModel, String username) {

        try {
            journalEntryModel.setDate(LocalDateTime.now());

            JournalEntryModel savedEntry = journalEntryRepository.save(journalEntryModel);

            UsersModel user = userService.findByUsername(username);

            user.getJournalEntries().add(savedEntry);

            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveEntry(JournalEntryModel journalEntryModel) {

        try {
            journalEntryModel.setDate(LocalDateTime.now());

            JournalEntryModel savedEntry = journalEntryRepository.save(journalEntryModel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<JournalEntryModel> getAllJournalEntries() {

        return journalEntryRepository.findAll();

    }

    public Optional<JournalEntryModel> getJournalByID(ObjectId journalID) {
        return journalEntryRepository.findById(journalID);

    }

    public boolean deleteByID(ObjectId journalID, String username) {

        boolean removed = false;

        try {
            UsersModel user = userService.findByUsername(username);

            removed = user.getJournalEntries().removeIf(journalEntryModel -> journalEntryModel.getId().equals(journalID));

            userService.saveUser(user);

            journalEntryRepository.deleteById(journalID);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting journal entry", e);
        }
        return removed;
    }

    public boolean deleteByID(ObjectId journalID) {

        boolean removed = false;

        try {
            journalEntryRepository.deleteById(journalID);
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting journal entry", e);
        }
        return removed;
    }


}
