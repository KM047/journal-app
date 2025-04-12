package com.kunal.journalApp.controllers;

import com.kunal.journalApp.models.JournalEntryModel;
import com.kunal.journalApp.models.UsersModel;
import com.kunal.journalApp.service.JournalEntryService;
import com.kunal.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs", description = "APIs related to journal entries")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping
    @Operation(summary = "Get all journal entries of a user")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        UsersModel user = userService.findByUsername(username);

        List<JournalEntryModel> allEntries = user.getJournalEntries();

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
    public ResponseEntity<?> findByID(@PathVariable("id") String  journalID) {

        ObjectId objectId = new ObjectId(journalID);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        UsersModel user = userService.findByUsername(username);

        List<JournalEntryModel> collected = user.getJournalEntries().stream().filter(j -> j.getId().equals(objectId)).toList();

        if (!collected.isEmpty()) {
            Optional<JournalEntryModel> userJournal = journalEntryService.getJournalByID(objectId);

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
    public ResponseEntity<JournalEntryModel> createJournalEntry(@RequestBody JournalEntryModel newJournalEntryModel) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();
            journalEntryService.saveEntry(newJournalEntryModel, username);
            return new ResponseEntity<>(newJournalEntryModel, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/u/{id}")
    public ResponseEntity<?> updateJournalEntry(@PathVariable("id") String journalID, @RequestBody JournalEntryModel updatedJournalEntryModel) {

        ObjectId objectId = new ObjectId(journalID);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        UsersModel user = userService.findByUsername(username);

        List<JournalEntryModel> collected = user.getJournalEntries().stream().filter(j -> j.getId().equals(objectId)).toList();


        if (!collected.isEmpty()) {
            JournalEntryModel oldJournalEntryModel = journalEntryService.getJournalByID(objectId).orElse(null);
            oldJournalEntryModel.setTitle(oldJournalEntryModel.getTitle() != null && !updatedJournalEntryModel.getTitle().isEmpty() ? updatedJournalEntryModel.getTitle() : oldJournalEntryModel.getTitle());
            oldJournalEntryModel.setContent(oldJournalEntryModel.getContent() != null && !updatedJournalEntryModel.getContent().isEmpty() ? updatedJournalEntryModel.getContent() : oldJournalEntryModel.getContent());

            journalEntryService.saveEntry(oldJournalEntryModel);

            return new ResponseEntity<>(
                    oldJournalEntryModel,
                    HttpStatus.OK
            );

        }

        return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
        );

    }

    @DeleteMapping("/d/{id}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable("id") String  journalID) {

        try {
            ObjectId objectId = new ObjectId(journalID);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();
            boolean removed = journalEntryService.deleteByID(objectId, username);

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
