package com.kunal.journalApp.controllers;

import com.kunal.journalApp.models.UsersModel;
import com.kunal.journalApp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;


//    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);


    @GetMapping("/health")
    public String healthCheck() {


        try {
            return "✅ Server is running fine!";
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            log.info("Checking server health.");
            log.warn("Checking server health.");
            log.error("Checking server health.");
            log.debug("Checking server health.");
            log.trace("Checking server health.");

        }


    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UsersModel user) {

        try {
            userService.saveNewUser(user);

            return new ResponseEntity<>(
                    user,
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            log.error("Error while creating for {} :",user.getUsername(),e);

            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }
}
