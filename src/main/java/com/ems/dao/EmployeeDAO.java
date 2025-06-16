package com.ems.dao;
import java.util.List;

import com.ems.entity.Employee;

	//Defines a DAO interface for Employee entity operations
	public interface EmployeeDAO {
	
	 // Adds a new employee to the database
	 void addEmployee(Employee employee);
	
	 // Returns the total number of employees in the system
	 long countEmployees();  // Method to count existing employees
	
	 // Retrieves an employee by their email address
	 Employee getEmployeeByEmail(String email);
	
	 // Returns a list of all employees
	 List<Employee> getAllEmployees();
	
	 // Checks if an email already exists in the database
	 boolean isEmailExists(String email);
	
	 // Retrieves an employee by their unique employee ID
	 Employee getEmployeeById(String employeeId);
	
	 // Deletes an employee from the database by employee ID
	 void deleteEmployeeById(String employeeId);
	
	 // Updates an existing employee's details
	 Employee updateEmployee(Employee employee);
	}


