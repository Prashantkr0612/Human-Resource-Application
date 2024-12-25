package com.cg.humanresource.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.humanresource.entity.Users;
import com.cg.humanresource.exception.UserNotFoundException;
import com.cg.humanresource.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository userRepository;
	
	@Transactional
	public boolean signIn(String username, String password) {
	    Optional<Users> user = Optional.ofNullable(userRepository.findByUsernameAndPassword(username,password));
	    return user.isPresent();
	}

	@Transactional
	public String signUp(Users user) {
		if (userRepository.existsById(user.getUsername())) {
			throw new RuntimeException("Username already exists");
		}
		userRepository.save(user);
		return "User registered successfully!";
	}
}
