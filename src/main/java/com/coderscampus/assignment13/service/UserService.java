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
import java.util.Set;

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

    public List<User> findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return userRepo.findByUsername(username);
    }

    public User findById(Long userId) {
        System.out.println("Before findById repo call");
        Optional<User> userOpt = userRepo.findById(userId);
        System.out.println("After findById repo call");
        return userOpt.orElse(new User());
    }

    public User newUser(User user) {
        if (user.getUserId() == null) {
            Account checking = new Account();
            checking.setAccountName("Checking Account");
            checking.getUsers().add(user);

            Account savings = new Account();
            savings.setAccountName("Savings Account");
            savings.getUsers().add(user);

            user.getAccounts().add(checking);
            user.getAccounts().add(savings);

            Address address = new Address();
            address.setUser(user);
            address.setUserId(user.getUserId());
            user.setAddress(address);
        }
        return userRepo.save(user);
    }

    public User saveUser(User user) {
        System.out.println("Start of saveUser");
        user.getAddress().setUserId(user.getUserId());
        user.getAddress().setUser(user);
        System.out.println("Just before repo call");

        return userRepo.save(user);
    }

    public void delete(Long userId) {
        userRepo.deleteById(userId);
    }

    public Set<User> findAll() {
        System.out.println("Before findAll repo call");
        return userRepo.findAllUsersWithAccountsAndAddresses();
    }
}
