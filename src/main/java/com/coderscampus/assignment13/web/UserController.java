//  7/10/24
//  Zack Laine
//  Assignment 13

package com.coderscampus.assignment13.web;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final AccountService accountService;
    UserService userService;

    UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String getCreateUser(ModelMap model) {
        model.put("user", new User());

        return "register";
    }

    @PostMapping("/register")
    public String postCreateUser(User user) {
        userService.newUser(user);

        return "redirect:/register";
    }

    @GetMapping("/users")
    public String getAllUsers(ModelMap model) {
        List<User> users = userService.findAll();
        users = users.stream().distinct().collect(Collectors.toList());

        model.put("users", users);
        if (users.size() == 1) {
            model.put("user", users.iterator().next());
        }
        return "users";
    }

    @GetMapping("/users/{userId}")
    public String getOneUser(ModelMap model, @PathVariable Long userId) {
        User user = userService.findById(userId);
        model.put("users", Collections.singletonList(user));
        model.put("user", user);
        return "user";
    }

    @PostMapping("/users/{userId}")
    public String postOneUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/users/" + user.getUserId();
    }

    @PostMapping("/users/{userId}/delete")
    public String postDeleteOneUser(@PathVariable Long userId) {
        userService.delete(userId);
        return "redirect:/users";
    }

    @PostMapping("users/{userId}/accounts")
    public String postAddAccount(@PathVariable Long userId, @ModelAttribute Account account) {
        userService.addAccount(userId);

        return "redirect:/users/" + userId;
    }

    @GetMapping("users/{userId}/accounts/{accountId}")
    public String getAccountEdit(ModelMap model, @PathVariable Long userId, @PathVariable Long accountId) throws AccountNotFoundException {
        User user = userService.findById(userId);
        Account account = accountService.findById(accountId);
        model.put("user", user);
        model.put("account", account);

        return "account";
    }

    @PostMapping("users/{userId}/accounts/{accountId}")
    public String postAccountEdit(User user, Account account) {
        accountService.saveAccount(user, account);
        return "redirect:/users/" + user.getUserId();
    }
}
