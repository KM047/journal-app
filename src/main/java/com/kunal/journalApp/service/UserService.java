package com.kunal.journalApp.service;

import com.kunal.journalApp.models.JournalEntry;
import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }


    public boolean saveUser(Users user) {

        Users savedUser = userRepository.save(user);

        if (savedUser == null) {
            return false;
        }
        return true;
    }

    public Optional<Users> findByID(ObjectId userID) {
        try {
            return userRepository.findById(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public boolean deleteUserByID(ObjectId userID) {
        try {
            userRepository.deleteById(userID);
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
