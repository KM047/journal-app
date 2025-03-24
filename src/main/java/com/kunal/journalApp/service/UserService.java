package com.kunal.journalApp.service;

import com.kunal.journalApp.models.UsersModel;
import com.kunal.journalApp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JournalEntryService journalEntryService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UsersModel> getAllUsers() {
        return userRepository.findAll();
    }


    public void saveUser(UsersModel user) {

        UsersModel savedUser = userRepository.save(user);

    }

    public boolean saveNewUser(UsersModel user) {


        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            user.setRoles(List.of("USER"));

            UsersModel savedUser = userRepository.save(user);

            if (user != null) {
                return true;
            }
        } catch (Exception e) {

            log.error("Error while creating user for {} : ", user.getUsername(), e);

            throw new RuntimeException(e);
        }

        return false;
    }

    public void saveNewAdmin(UsersModel user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRoles(List.of("USER", "ADMIN"));

        userRepository.save(user);
    }

    public Optional<UsersModel> findByID(ObjectId userID) {
        try {
            return userRepository.findById(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteUserByUsername(String username) {
        try {

            UsersModel user = userRepository.findByUsername(username);

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

    public UsersModel findByUsername(@RequestBody String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }




}
