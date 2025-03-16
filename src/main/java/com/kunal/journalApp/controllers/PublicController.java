package com.kunal.journalApp.controllers;

import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health")
    public String healthCheck() {
        return "✅ Server is running fine!";
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Users user) {

        try {
            userService.saveNewUser(user);

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
}
