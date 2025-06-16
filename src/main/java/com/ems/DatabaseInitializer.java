
package com.ems;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ems.dao.UserDAO;
import com.ems.entity.Role;
import com.ems.entity.User;


//Marks this class as a source of bean definitions for the Spring container
@Configuration
public class DatabaseInitializer {

 // Defines a bean that runs specific code when the application starts
 @Bean
 CommandLineRunner initDatabase(UserDAO userRepository, PasswordEncoder passwordEncoder) {
     // Lambda expression to run when the application starts
     return args -> {
         // Check if the User table is empty
         if (userRepository.count() == 0) {
             // Create a new User object for the admin
             User admin = new User();
             
             // Set admin email
             admin.setEmail("sid@gmail.com");
             
             // Encode and set admin password
             admin.setPassword(passwordEncoder.encode("sid123"));
             
             // Set role as ADMIN
             admin.setRole(Role.ADMIN);
             
             // Save the admin user to the database
             userRepository.save(admin);
             
             // Print the encoded password to the console (for debugging/logging)
             System.out.println("Admin password stored as: " + admin.getPassword());
         }
     };
 }
}
