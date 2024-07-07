package com.coderscampus.assignment13.service;

import com.coderscampus.assignment13.domain.Address;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.AddressRepository;
import com.coderscampus.assignment13.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AddressService {

    private final UserRepository userRepo;
    private final AccountRepository accountRepo;
    private final AddressRepository addressRepo;

    public AddressService(UserRepository userRepo, AccountRepository accountRepo, AddressRepository addressRepo) {
        this.userRepo = userRepo;
        this.accountRepo = accountRepo;
        this.addressRepo = addressRepo;
    }

//    public Address saveAddress(Address address, User user) {
//        if(!Objects.equals(address.getUserId(), user.getUserId())){
//            address.setUserId(user.getUserId());
//            address.setUser(user);
//            user.setAddress(address);
//        }
//
//        return addressRepo.save(address);
//    }
}
