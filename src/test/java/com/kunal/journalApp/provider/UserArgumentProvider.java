package com.kunal.journalApp.provider;

import com.kunal.journalApp.models.Users;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Stream;

    public class UserArgumentProvider implements ArgumentsProvider {


    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(
                Arguments.of(Users.builder().username("user10").password("pass").roles(List.of("USER")).build()),
                Arguments.of(Users.builder().username("user20").password("pass").roles(List.of("USER")).build())
        );
    }
}
