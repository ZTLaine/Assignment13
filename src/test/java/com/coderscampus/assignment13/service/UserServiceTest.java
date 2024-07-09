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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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
        userRepo.deleteAll();

        user1 = new User();
        user2 = new User();

        name1 = "Test name 1";
        name2 = "Test name 2";
        user1.setName(name1);
        user2.setName(name2);

        username1 = "Test username 1";
        username2 = "Test username 2";
        user1.setUsername(username1);
        user2.setUsername(username2);

        password1 = "Test password 1";
        password2 = "Test password 2";
        user1.setPassword(password1);
        user2.setPassword(password2);

        userRepo.save(user1);
        userRepo.save(user2);

    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();
    }

    @Test
    void Find_All() {
        List<User> users = userService.findAll();

        assertEquals(2, users.size());
        assertEquals(user1, users.get(0));
        assertEquals(user2, users.get(1));
        assertEquals(name1, users.get(0).getName());
        assertEquals(name2, users.get(1).getName());
        assertEquals(username1, users.get(0).getUsername());
        assertEquals(username2, users.get(1).getUsername());
        assertEquals(password1, users.get(0).getPassword());
        assertEquals(password2, users.get(1).getPassword());
    }

    @Test
    void Find_All_When_Empty(){
        userRepo.deleteAll();
        List<User> users = userService.findAll();

        assertEquals(0, users.size());
    }

    @Test
    void Find_By_Id() {
        User testUser1 = userService.findById(user1.getUserId());
        User testUser2 = userService.findById(user2.getUserId());

        assertEquals(user1, testUser1);
        assertEquals(user2, testUser2);

        assertThrows(RuntimeException.class, () -> userService.findById(200L));
        assertThrows(IllegalArgumentException.class, () -> userService.findById(-1L));
        assertThrows(IllegalArgumentException.class, () -> userService.findById(null));
    }

    @Test
    void New_User(){
        User testUser1 = userService.newUser(user1);
        User testUser2 = userService.newUser(user2);
        User newUser = userService.newUser(new User());

        assertEquals(user1, testUser1);
        assertEquals(user2, testUser2);
        assertNotNull(newUser.getUserId());
        assertEquals(newUser.getAccounts().get(0).getAccountName(),
                "Checking Account");
        assertTrue(newUser.getAccounts().get(0).getUsers().contains(newUser));
        assertThrows(IllegalArgumentException.class, () -> userService.newUser(null));
    }

    @Test
    void Save_User() {
        User userWithAccounts = userService.newUser(new User());
        String addressTest = "Test address";
        userWithAccounts.getAddress().setAddressLine1(addressTest);
        userWithAccounts = userService.saveUser(userWithAccounts);

        assertEquals(userWithAccounts.getAddress().getAddressLine1(), addressTest);
        assertEquals(userWithAccounts.getAccounts().get(0).getAccountName(),
                "Checking Account");
    }

    @Test
    void Delete() {
    }
}