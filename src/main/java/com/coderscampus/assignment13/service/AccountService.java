//  7/10/24
//  Zack Laine
//  Assignment 13

package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private UserService userService;

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;

    AccountService(UserRepository userRepo, AccountRepository accountRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
    }

    public Account findById(Long accountId) throws AccountNotFoundException {
        Optional<Account> accountOpt = accountRepo.findById(accountId);
        if (accountOpt.isPresent()) {
            return accountOpt.get();
        } else
            throw new AccountNotFoundException("An account with Id " + accountId + " doesn't exist!");
    }

    public Account saveAccount(User user, Account account) {
        if (!accountRepo.existsById(account.getAccountId())) {
            account.getUsers().add(user);
            user.getAccounts().add(account);
            userRepo.save(user);
        }
        System.out.println("Just before accountRepo save is called");
        return accountRepo.save(account);
    }

    public User addNewAccount(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null!");
        }

        Account account = new Account();
        User user = userService.findById(userId);
        if (user.getAccounts() == null) {
            user.setAccounts(new ArrayList<>());
        }

        account.setAccountName("Account #" + (user.getAccounts().size() + 1));
        user.getAccounts().add(account);
        account.setUsers(new ArrayList<>());
        account.getUsers().add(user);

        return user;
    }
}
