package com.kunal.journalApp.controllers;

import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {

        try {

            List<Users> allUsers = userService.getAllUsers();

            if (!allUsers.isEmpty()) {

                return new ResponseEntity<>(
                        allUsers,
                        HttpStatus.OK
                );

            }

            return new ResponseEntity<>(

                    HttpStatus.OK
            );


        } catch (Exception e) {
            throw new RuntimeException("Error while getting all users", e);
        }
    }

    @PostMapping("/add-admin")
    public ResponseEntity<?> createNewAdmin(@RequestBody Users user) {

        try {
            userService.saveNewAdmin(user);

            return new ResponseEntity<>(
                    user,
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            System.out.println("Error while creating new admin " + e);

            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping("/d/u/{username}")
    public ResponseEntity<?> deleteUsersByID(@PathVariable String username) {

        try {

            userService.deleteUserByUsername(username);

            return new ResponseEntity<>(
                    "User deleted successfully",
                    HttpStatus.OK
            );
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all users", e);
        }
    }


}
