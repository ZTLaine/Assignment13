//  7/10/24
//  Zack Laine
//  Assignment 13

package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final AccountService accountService;


    UserService(UserRepository userRepo, AccountService accountService) {
        this.userRepo = userRepo;
        this.accountService = accountService;
    }

    public User findById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null!");
        }

        if (userId < 0) {
            throw new IllegalArgumentException("userId cannot be negative!");
        }

        Optional<User> userOpt = userRepo.findById(userId);
        return userOpt.orElseThrow(() -> new RuntimeException("User not found!"));
    }

    public User newUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("user cannot be null!");
        }

        if (user.getUserId() == null) {
            Account checking = new Account();
            checking.setAccountName("Checking Account");
            checking.getUsers().add(user);

            Account savings = new Account();
            savings.setAccountName("Savings Account");
            savings.getUsers().add(user);

            user.getAccounts().add(checking);
            user.getAccounts().add(savings);

            addAddress(user, new Address());
        }
        return userRepo.save(user);
    }

    public User saveUser(User user) {
        User existingUser = findById(user.getUserId());

        if (existingUser != null) {
            if(!user.getUsername().isEmpty()) {
                existingUser.setUsername(user.getUsername());
            }
            if (!user.getPassword().isEmpty()) {
                existingUser.setPassword(user.getPassword());
            }
            existingUser.setName(user.getName());
            existingUser.setAddress(user.getAddress());
        }

        existingUser = addAddress(existingUser, user.getAddress());

        return userRepo.save(existingUser);
    }

    public void delete(Long userId) {
        userRepo.deleteById(userId);
    }

    public List<User> findAll() {
        return userRepo.findAllUsersWithAccountsAndAddresses();
    }

    public User addAddress(User user, Address address) {
        if (user == null || address == null) {
            throw new IllegalArgumentException("user or address cannot be null!");
        }

        address.setUser(user);
        user.setAddress(address);

        if (user.getUserId() != null) {
            address.setUserId(user.getUserId());
        }

        return user;
    }

    public void addAccount(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null!");
        }

        User user = accountService.addNewAccount(userId);
        userRepo.save(user);
    }
}
