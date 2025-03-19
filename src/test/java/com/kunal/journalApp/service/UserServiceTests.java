//package com.kunal.journalApp.service;
//
//
//import com.kunal.journalApp.models.Users;
//import com.kunal.journalApp.provider.UserArgumentProvider;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.ArgumentsSource;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//@Disabled("Demo purposes")
//public class UserServiceTests {
//
//    @Autowired
//    private UserService userService;
//
//    @BeforeAll
//    public static void beforeAll() {
//        System.out.println("Before all");
//    }
//
//    @AfterAll
//    public static void afterAll() {
//        System.out.println("After all");
//    }
//
//    @Test
//    public void test() {
//        assertEquals(4, 2 + 2);
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {
//            "admin",
////            "admin1",
//            "admin2",
//    })
//    public void findUserByUsername(String username) {
//        assertNotNull(userService.findByUsername(username), "User not found");
//    }
//
////    @ParameterizedTest
////    @ArgumentsSource(UserArgumentProvider.class)
////    public void createNewUserTest(Users user) {
////        assertTrue(userService.saveNewUser(user), "Error while creating new user");
////    }
//
//    @ParameterizedTest
//    @CsvSource(value = {
//            "1, 2, 3",
//            "2, 3, 5",
//            "3, 4, 7"
//    })
//    public void test(int a, int b, int result) {
//        assertEquals(result, a + b, "The result is not correct for " + a + " + " + b + " = " + result);
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//        System.out.println("Before each");
//    }
//
//    @AfterEach
//    public void afterEach() {
//        System.out.println("After each");
//    }
//
//
//}
