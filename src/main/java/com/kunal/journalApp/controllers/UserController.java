package com.kunal.journalApp.controllers;


import com.kunal.journalApp.api.response.WeatherResponse;
import com.kunal.journalApp.models.UsersModel;
import com.kunal.journalApp.service.UserService;
import com.kunal.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/u/{id}")
    public ResponseEntity<?> findUserByID(@PathVariable("id") ObjectId userID) {

        Optional<UsersModel> user = userService.findByID(userID);

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
    public ResponseEntity<?> updateUser(@RequestBody UsersModel user) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String username = authentication.getName();

            UsersModel userInDB = userService.findByUsername(username);

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
    
    @GetMapping
    public ResponseEntity<?> getUser()  {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        authentication.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));

        WeatherResponse weatherResponse = weatherService.getWeather("Vaduj");

        if (weatherResponse != null) {

            return new ResponseEntity<>(
                    username + " Welcome you weather feels like " +weatherResponse.getCurrent().getTemperature(),
                    HttpStatus.OK
            );
        }


        return new ResponseEntity<>(
                " Welcome " + username,
                HttpStatus.OK
        );
    }


}
