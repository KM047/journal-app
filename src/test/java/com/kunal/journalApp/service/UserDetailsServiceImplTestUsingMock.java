package com.kunal.journalApp.service;

import com.kunal.journalApp.models.Users;
import com.kunal.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.mockito.Mockito.when;


public class UserDetailsServiceImplTestUsingMock {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {

        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(Users.builder().username("admin").password("admin").roles(List.of("USER")).build());

        UserDetails admin = userDetailsService.loadUserByUsername("admin");

        Assertions.assertNotNull(admin);
    }
}
