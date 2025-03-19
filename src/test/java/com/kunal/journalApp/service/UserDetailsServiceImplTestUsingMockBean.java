package com.kunal.journalApp.service;

import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.Mockito.when;


@SpringBootTest
class UserDetailsServiceImplTestUsingMockBean {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockitoBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {

        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Users.builder().username("admin").password("admin").roles(List.of("USER")).build());

        UserDetails admin = userDetailsService.loadUserByUsername("admin");

        Assertions.assertNotNull(admin);
    }
}
