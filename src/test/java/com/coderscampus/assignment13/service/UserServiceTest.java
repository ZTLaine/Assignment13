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

    @BeforeEach
    void setUp() {
        user1 = new User();
        user2 = new User();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void findByUsername() {
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