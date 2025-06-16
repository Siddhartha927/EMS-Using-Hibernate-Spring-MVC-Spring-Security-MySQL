package com.ems.Controller;
import com.ems.Service.EmployeeService;
import com.ems.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

//Marks this class as a Spring MVC controller to handle web requests
@Controller

//Maps requests that start with "/employeeDashboard" to this controller
@RequestMapping("/employeeDashboard")
public class EmployeeController {
	
	// Injects the EmployeeService bean to access employee-related operations
	@Autowired
 private EmployeeService  employeeService;
	
	// Handles HTTP GET requests to "/employeeDashboard"
	@GetMapping("")
	public String getEmployeedashboard(Model model) {
		// Gets the currently authenticated user's details from the security context
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// Retrieves the email (username) of the logged-in employee
		String email = authentication.getName();
		
		// Uses the email to fetch the corresponding Employee object from the database
		Employee employee = employeeService.getEmployeeByEmail(email);
		
		// Adds the employee object to the model so it can be accessed in the Thymeleaf view
		model.addAttribute("employee", employee);
		
		// Returns the name of the view (employeeDashboard.html) to be rendered
		return "employeeDashboard";
	}
	
}

