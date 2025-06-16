package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Indicates a Spring Boot application and triggers auto-configuration, component scanning, and configuration
@SpringBootApplication(scanBasePackages = "com.ems")
public class EMSApplication {

	// Entry point of the Spring Boot application
	public static void main(String[] args) {
		// Launches the application by starting the Spring context
		SpringApplication.run(EMSApplication.class, args);
	}
}

