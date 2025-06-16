package com.ems.dao;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.ems.entity.User;

	//Marks this interface as a Spring repository for dependency injection and persistence
	@Repository
	public interface UserDAO {

	// Finds a user by their email address and returns an Optional
	public Optional<User> findByEmail(String email);
	
	// Saves a User entity to the database
	public void save(User user);
	
	// Returns the total count of users in the database
	public long count();
}


