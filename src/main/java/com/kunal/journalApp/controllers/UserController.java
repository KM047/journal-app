package com.kunal.journalApp.controllers;


import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

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

    @DeleteMapping
    public boolean deleteUserByUsername() {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            userService.deleteUserByUsername(username);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody Users user) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            Users userInDB = userService.findByUsername(username);

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
