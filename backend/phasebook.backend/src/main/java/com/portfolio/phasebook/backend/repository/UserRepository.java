package com.portfolio.phasebook.backend.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.phasebook.backend.entity.User;

public interface UserRepository extends JpaRepository<User,Integer> {
 //   User findByUserName(String username);
    
	Page<User> findByLastName(String lastName, org.springframework.data.domain.Pageable pageable);

	Page<User> findByFirstNameLike(String firstName, org.springframework.data.domain.Pageable pageable);

	//finding a user by email
	//List<User> findFirst1ByEmail(String email);
	
	List<User> findFirst1ByEmail(String email);
    
	User findByEmail(String email);

	
}
