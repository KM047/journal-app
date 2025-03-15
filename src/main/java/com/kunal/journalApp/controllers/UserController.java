package com.kunal.journalApp.controllers;


import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        List<Users> allUsers = userService.getAllUsers();

        if (allUsers.isEmpty()) {
            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                allUsers,
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Users user) {

        try {
            userService.saveUser(user);

            return new ResponseEntity<>(
                    user,
                    HttpStatus.CREATED
            );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/u/{id}")
    public ResponseEntity<?> findUserByID(@PathVariable("id") ObjectId userID) {

        Optional<Users> user = userService.findByID(userID);

        if (user.isEmpty()) {

            return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                user,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/d/{id}")
    public boolean deleteUserByID(@PathVariable("id") ObjectId userID) {

        try {
            userService.deleteUserByID(userID);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    @PutMapping("/{username}")
    public ResponseEntity<?> updateUser(@RequestBody Users user, @PathVariable String username) {

        try {
            Users userInDB = userService.findByUsername(username);

            if (userInDB == null) {
                return new ResponseEntity<>(
                        HttpStatus.NOT_FOUND
                );
            }

            userInDB.setUsername(user.getUsername() != null ? user.getUsername() : userInDB.getUsername());
            userInDB.setPassword(user.getPassword() != null ? user.getPassword() : userInDB.getPassword());

            userService.saveUser(userInDB);

            return new ResponseEntity<>(
                    userInDB,
                    HttpStatus.OK
            );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }


}
