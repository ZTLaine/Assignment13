//  7/10/24
//  Zack Laine
//  Assignment 13

package com.coderscampus.assignment13.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.assignment13.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u"
		+ " left join fetch u.accounts"
		+ " left join fetch u.address")
	List<User> findAllUsersWithAccountsAndAddresses();
}
