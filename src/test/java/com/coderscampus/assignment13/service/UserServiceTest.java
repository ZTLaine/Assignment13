package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.AddressRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private AddressRepository addressRepo;

    private User user1;
    private User user2;

    private String name1;
    private String name2;

    private String username1;
    private String username2;

    private String password1;
    private String password2;

    @BeforeEach
    void setUp() {
        user1 = new User();
        user2 = new User();

        name1 = "Test name 1";
        name2 = "Test name 2";

        username1 = "Test username 1";
        username2 = "Test username 2";

        password1 = "Test password 1";
        password2 = "Test password 2";

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    @Transactional
    void findByUsername() {
        assertEquals(userService.findByUsername(username1).get(0).getUsername(), username1);
        assertEquals(userService.findByUsername(username2).get(0).getUsername(), username2);
        assertNotEquals(userService.findByUsername(username1).get(0).getUsername(), username2);
        assertNotEquals(userService.findByUsername(username2).get(0).getUsername(), username1);
        assertThrows(IllegalArgumentException.class, () -> userService.findByUsername(null));
        assertThrows(IllegalArgumentException.class, () -> userService.findByUsername(""));
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void delete() {
    }
}