package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;

    UserService(UserRepository userRepo, AccountRepository accountRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
    }

    public List<User> findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        return userRepo.findByUsername(username);
    }


    public User findById(Long userId) {
        Optional<User> userOpt = userRepo.findById(userId);
        return userOpt.orElse(new User());
    }

    public User saveUser(User user) {
        if (user.getUserId() == null) {
            Address address = new Address();
            address.setUser(user);
            address.setUserId(user.getUserId());

            Account checking = new Account();
            checking.setAccountName("Checking Account");
            checking.getUsers().add(user);
            Account savings = new Account();
            savings.setAccountName("Savings Account");
            savings.getUsers().add(user);

            user.getAccounts().add(checking);
            user.getAccounts().add(savings);
            accountRepo.save(checking);
            accountRepo.save(savings);
        }
        return userRepo.save(user);
    }

    public void delete(Long userId) {
        userRepo.deleteById(userId);
    }

    public Set<User> findAll() {
        return userRepo.findAllUsersWithAccountsAndAddresses();
    }

    //	public List<User> findByNameAndUsername(String name, String username) {
//		return userRepo.findByNameAndUsername(name, username);
//	}
//
//	public List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2) {
//		return userRepo.findByCreatedDateBetween(date1, date2);
//	}
//
//	public User findExactlyOneUserByUsername(String username) {
//		List<User> users = userRepo.findExactlyOneUserByUsername(username);
//		if (!users.isEmpty())
//			return users.get(0);
//		else
//			return new User();
//	}
}
