package com.kunal.journalApp.controllers;

import com.kunal.journalApp.dto.UserDTO;
import com.kunal.journalApp.models.UsersModel;
import com.kunal.journalApp.service.JWTService;
import com.kunal.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
@Tag(name = "Public APIs", description = "These APIs are used to manage public data.")
public class PublicController {

    @Autowired
    private UserService userService;


//    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

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
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) {

        try {

            UsersModel newUser = new UsersModel();

            newUser.setEmail(user.getEmail());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());

            userService.saveNewUser(newUser);

            return new ResponseEntity<>(
                    user,
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            log.error("Error while creating for {} :", user.getUsername(), e);

            return new ResponseEntity<>(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserDTO user) {

        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//
//            UsersModel byUsername = userService.findByUsername(user.getUsername());

            String token = userService.login(user);

            return new ResponseEntity<>(
                    token,
                    HttpStatus.OK
            );


        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }


    }

}
