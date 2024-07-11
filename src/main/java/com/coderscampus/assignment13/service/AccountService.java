//  7/10/24
//  Zack Laine
//  Assignment 13

package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

@Service
public class AccountService {

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final UserService userService;

    AccountService(UserRepository userRepo, AccountRepository accountRepo, UserService userService) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.userService = userService;
    }

    public Account findById(Long accountId) throws AccountNotFoundException {
        Optional<Account> accountOpt = accountRepo.findById(accountId);
        System.out.println("After accountRepo findById is called");
        if (accountOpt.isPresent()) {
            return accountOpt.get();
        } else
            throw new AccountNotFoundException("An account with Id " + accountId + " doesn't exist!");
    }

    public Account saveAccount(User user, Account account) {
        if(!accountRepo.existsById(account.getAccountId())) {
            account.getUsers().add(user);
            user.getAccounts().add(account);
            userRepo.save(user);
        }
        System.out.println("Just before accountRepo save is called");
        return accountRepo.save(account);
    }

    public User addAccount(Long userId) {
        User user = userService.findById(userId);
        user.getAccounts().add(new Account());

    }
}
