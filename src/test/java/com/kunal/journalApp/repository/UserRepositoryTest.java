package com.kunal.journalApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    void userRepositoryTesting() {

        userRepositoryImpl.getUsersBySA();



    }


}
