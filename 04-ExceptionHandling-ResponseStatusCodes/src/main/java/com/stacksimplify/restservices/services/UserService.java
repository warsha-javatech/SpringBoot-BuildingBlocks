package com.stacksimplify.restservices.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.stacksimplify.restservices.entities.User;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNotFoundException;
import com.stacksimplify.restservices.repositories.UserRepository;


// Service
@Service
public class UserService {
	
	// Autowire the UserRepository
	@Autowired
	private UserRepository userRepository;
	
	// getAllUsers() method
	public List<User>  getAllUsers() {
		return userRepository.findAll();
	}
	
	// createUser() method
	public User createUser(User user) throws UserExistsException{
		// if user exist using username
		User existingUser = userRepository.findByUsername(user.getUsername());
		
		// if user exists throw UserExistsException
		if(existingUser != null) {
			throw new UserExistsException("User already exists in repository");
		}
		
		
		// if user not exists
		return userRepository.save(user);
	}
	
	// getUserById() method
	public Optional<User> getUserById(Long id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("User Not found in User Repository");
		}
		return user;
	}
	
	// UpdateUserById
	public User updateUserById(Long id, User user) throws UserNotFoundException {
		Optional<User> optionalUser = userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new UserNotFoundException("User Not found in User Repository, provide the correct user id");
		}
		
		user.setId(id);
		return userRepository.save(user);
	}
	
	// deleteUserById
	public void deleteUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if(!optionalUser.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User Not found in User Repository, provide the correct user id");
		}
		userRepository.deleteById(id);
	}
	
	// getUserByUsername()
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
