package com.kunal.journalApp.service;

import com.kunal.journalApp.models.JournalEntry;
import com.kunal.journalApp.models.Users;
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
    public void saveEntry(JournalEntry journalEntry, String username) {

        try {
            journalEntry.setDate(LocalDateTime.now());

            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);

            Users user = userService.findByUsername(username);

            user.getJournalEntries().add(savedEntry);

            userService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveEntry(JournalEntry journalEntry) {

        try {
            journalEntry.setDate(LocalDateTime.now());

            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<JournalEntry> getAllJournalEntries() {

        return journalEntryRepository.findAll();

    }

    public Optional<JournalEntry> getJournalByID(ObjectId journalID) {
        return journalEntryRepository.findById(journalID);

    }

    public boolean deleteByID(ObjectId journalID, String username) {

        boolean removed = false;

        try {
            Users user = userService.findByUsername(username);

            removed = user.getJournalEntries().removeIf(journalEntry -> journalEntry.getId().equals(journalID));

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
