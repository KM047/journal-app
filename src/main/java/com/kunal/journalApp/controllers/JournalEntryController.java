package com.kunal.journalApp.controllers;

import com.kunal.journalApp.models.JournalEntry;
import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.service.JournalEntryService;
import com.kunal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username) {

        Users user = userService.findByUsername(username);

        List<JournalEntry> allEntries = user.getJournalEntries();

        if (allEntries.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(
                allEntries,
                HttpStatus.OK
        );


    }

    @GetMapping("/j/{id}")
    public ResponseEntity<?> findByID(@PathVariable("id") ObjectId journalID) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = userService.findByUsername(username);

        List<JournalEntry> collected = user.getJournalEntries().stream().filter( j -> j.getId().equals(journalID)).toList();

        if (!collected.isEmpty()) {
            Optional<JournalEntry> userJournal = journalEntryService.getJournalByID(journalID);

            if (userJournal.isPresent()) {
                return new ResponseEntity<>(
                        userJournal.get(),
                        HttpStatus.OK
                );
            }

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry newJournalEntry) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();
            journalEntryService.saveEntry(newJournalEntry, username);
            return new ResponseEntity<>(newJournalEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/u/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable("id") ObjectId journalID, @RequestBody JournalEntry updatedJournalEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = userService.findByUsername(username);

        List<JournalEntry> collected = user.getJournalEntries().stream().filter( j -> j.getId().equals(journalID)).toList();


        if (!collected.isEmpty()) {
            JournalEntry oldJournalEntry = journalEntryService.getJournalByID(journalID).orElse(null);
            oldJournalEntry.setTitle(oldJournalEntry.getTitle() != null && !updatedJournalEntry.getTitle().equals("") ? updatedJournalEntry.getTitle() : oldJournalEntry.getTitle());
            oldJournalEntry.setContent(oldJournalEntry.getContent() != null && !updatedJournalEntry.getContent().equals("") ? updatedJournalEntry.getContent() : oldJournalEntry.getContent());

            journalEntryService.saveEntry(oldJournalEntry);

            return new ResponseEntity<>(
                    oldJournalEntry,
                    HttpStatus.OK
            );

        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );

    }

    @DeleteMapping("/d/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable("id") ObjectId journalID) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();
            boolean removed = journalEntryService.deleteByID(journalID, username);

            if (removed) {
                return new ResponseEntity<>("Entry Deleted Successfully",HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
