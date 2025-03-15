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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<?> findByID(@PathVariable ObjectId id) {

        Optional<JournalEntry> journalEntry = journalEntryService.findByID(id);

        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatusCode.valueOf(200));
        }

        return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));


    }

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry newJournalEntry, @PathVariable String username) {

        try {
            journalEntryService.saveEntry(newJournalEntry, username);
            return new ResponseEntity<>(newJournalEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("u/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId id, @RequestBody JournalEntry updatedJournalEntry) {

        JournalEntry oldJournalEntry = journalEntryService.findByID(id).orElse(null);

        if (oldJournalEntry != null) {

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

    @DeleteMapping("/d/{username}/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable("id") ObjectId journalID, @PathVariable String username) {

        try {
            journalEntryService.deleteByID(journalID, username);
            return new ResponseEntity<>("Entry Deleted Successfully",HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
