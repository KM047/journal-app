package com.kunal.journalApp.service;

import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JournalEntryService journalEntryService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


    public void saveUser(Users user) {

        Users savedUser = userRepository.save(user);

    }

    public void saveNewUser(Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(List.of("USER"));

        userRepository.save(user);
    }

    public void saveNewAdmin(Users user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(List.of("USER", "ADMIN"));

        userRepository.save(user);
    }

    public Optional<Users> findByID(ObjectId userID) {
        try {
            return userRepository.findById(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteUserByUsername(String username) {
        try {

            Users user = userRepository.findByUsername(username);

            if (user == null) {
                return false;
            }

            user.getJournalEntries().forEach(journalEntry -> journalEntryService.deleteByID(journalEntry.getId()));

            userRepository.deleteByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public Users findByUsername(@RequestBody String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




}
