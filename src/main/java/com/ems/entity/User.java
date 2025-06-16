package com.ems.entity;

import jakarta.persistence.*;

//Marks this class as a JPA entity (i.e., mapped to a database table)
@Entity

//Specifies the table name in the database as "users"
@Table(name = "users")
public class User {

	 // Marks this field as the primary key of the entity
	 @Id
	
	 // Specifies that the primary key will be auto-generated using the database's identity strategy (like AUTO_INCREMENT)
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 // Email field to store the user's email address
	 private String email;
	
	 // Password field to store the user's password
	 private String password;
	
	 // Specifies that the enum value should be stored as a String in the DB (e.g., "ADMIN", "EMPLOYEE")
	 @Enumerated(EnumType.STRING)
	 private Role role;
	
	 // Getter method for id
		public Long getId() {
			return id;
		}
	
	 // Setter method for id
		public void setId(Long id) {
			this.id = id;
		}
	
	 // Getter method for email
		public String getEmail() {
			return email;
		}
	
	 // Setter method for email
		public void setEmail(String email) {
			this.email = email;
		}
	
	 // Getter method for password
		public String getPassword() {
			return password;
		}
	
	 // Setter method for password
		public void setPassword(String password) {
			this.password = password;
		}
	
	 // Getter method for role
		public Role getRole() {
			return role;
		}
	
	 // Setter method for role
		public void setRole(Role role) {
			this.role = role;
		}
}
