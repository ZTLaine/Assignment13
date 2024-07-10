package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.AddressRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final AddressRepository addressRepo;
    private final AddressService addressService;

    UserService(UserRepository userRepo, AccountRepository accountRepo, AddressRepository addressRepo, AddressService addressService) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.addressRepo = addressRepo;
        this.addressService = addressService;
    }

    public User findById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null!");
        }

        if (userId < 0) {
            throw new IllegalArgumentException("userId cannot be negative!");
        }

        System.out.println("Before findById repo call");
        Optional<User> userOpt = userRepo.findById(userId);
        System.out.println("After findById repo call");
        return userOpt.orElseThrow(() -> new RuntimeException("User not found."));
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
//            Address address = new Address();
//            address.setUser(user);
//            address.setUserId(user.getUserId());
//            user.setAddress(address);
        }
        return userRepo.save(user);
    }

    //    Why does the user getting passed into this have no accounts?
//    Why is the join table cleared??
    public User saveUser(User user) {
        User existingUser = findById(user.getUserId());

        if (existingUser != null) {
            existingUser.setName(user.getName());
            existingUser.setUsername(user.getUsername());
            existingUser.setPassword(user.getPassword());
            existingUser.setAddress(user.getAddress());
        }

        existingUser = addAddress(existingUser, user.getAddress());

//        user.getAddress().setUserId(user.getUserId());
//        user.getAddress().setUser(user);

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
}
