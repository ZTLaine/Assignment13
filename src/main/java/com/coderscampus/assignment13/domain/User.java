//  7/10/24
//  Zack Laine
//  Assignment 13

package com.coderscampus.assignment13.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity // Class name = User, DB Table name = user
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Address address;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("accountId ASC")
    @JoinTable(name = "user_account",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> accounts = new ArrayList<>();

    private String username;
    private String password;
    private String name;
    private LocalDate createdDate;

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", name=" + name
                + ", accounts=" + accounts + ", address=" + address + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        User other = (User) obj;
        if (userId == null) {
            return other.userId == null;
        } else return userId.equals(other.userId);
    }
}
