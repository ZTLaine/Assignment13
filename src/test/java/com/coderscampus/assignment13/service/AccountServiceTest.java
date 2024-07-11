package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserRepository userRepo;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userRepo.deleteAll();

        user1 = new User();
        user2 = new User();

        userRepo.save(user1);
        userRepo.save(user2);
    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    @Test
    void Find_By_Id() {
    }

    @Test
    void Save_Account() {
    }

    @Test
    @Transactional
    void Add_New_Account() {
        user1 = accountService.addNewAccount(user1.getUserId());
        user1 = accountService.addNewAccount(user1.getUserId());
        user2 = accountService.addNewAccount(user2.getUserId());

        userRepo.save(user1);
        userRepo.save(user2);

        assertNotNull(user1.getAccounts().get(0));
        assertNotNull(user1.getAccounts().get(1));
        assertNotNull(user2.getAccounts().get(0));
        assertNull(user1.getAccounts().get(0).getAccountName());
        assertEquals(user1.getUserId(), user1.getAccounts().get(0).getUsers().get(0).getUserId());
        assertEquals(user1.getUserId(), user1.getAccounts().get(1).getUsers().get(0).getUserId());
        assertEquals(user2.getUserId(), user2.getAccounts().get(0).getUsers().get(0).getUserId());

        assertThrows(IllegalArgumentException.class, () -> accountService.addNewAccount(null));
    }
}