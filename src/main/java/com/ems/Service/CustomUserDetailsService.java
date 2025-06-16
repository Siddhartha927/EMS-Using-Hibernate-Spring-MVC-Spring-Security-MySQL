package com.ems.Service;



import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ems.dao.UserDAO;
import com.ems.entity.User;

	//Marks this class as a Spring service component
	@Service
	
	//Ensures that methods execute within a transactional context
	@Transactional
	public class CustomUserDetailsService implements UserDetailsService {
	
		// Injects an instance of UserDAO for database operations
		@Autowired
	 private UserDAO userDAO;
	
	 // Overriding the method from UserDetailsService to load user details by email
	 @Override
	 public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	     // Fetches the user from the database using the email, or throws an exception if not found
	     User user = userDAO.findByEmail(email)
	         .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
	     
	     // Returns a Spring Security UserDetails object with the user's email, password, and role
	     return new org.springframework.security.core.userdetails.User(
	         user.getEmail(), // Sets the username (email)
	         user.getPassword(), // Sets the password
	         Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())) // Assigns a role
	     );
	 }
	}
