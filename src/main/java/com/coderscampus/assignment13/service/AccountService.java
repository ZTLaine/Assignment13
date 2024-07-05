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
        if(!accountRepo.existsById(account.getAccountId())) {
            account.getUsers().add(user);
            user.getAccounts().add(account);
            userRepo.save(user);
        }
        return accountRepo.save(account);
    }
}
