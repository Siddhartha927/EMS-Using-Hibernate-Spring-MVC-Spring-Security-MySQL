package com.ems.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


	//Marks this class as a Spring MVC Controller
	@Controller
	public class loginController {
	
	 // Handles GET request for the login page
	 @GetMapping("/login")
	 public String showLoginPage() {
	     // Returns the login view
	     return "login";
	 }
	
	 // Handles GET request to redirect user based on their role after successful login
	 @GetMapping("/redirect-by-role")
	 public String redirectByRole() {
	     // Retrieves the currently authenticated user's information
	     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     
	     // Checks if the authentication and authorities are not null
	     if(authentication != null && authentication.getAuthorities() != null) {
	         // Loops through the authorities (roles) of the authenticated user
	         for(GrantedAuthority authority : authentication.getAuthorities()) {
	             
	             // Redirects based on the user's role
	             switch (authority.getAuthority()){
	                 case "ROLE_ADMIN":
	                     return "redirect:/adminDashboard";
	                 case "ROLE_EMPLOYEE":
	                     return "redirect:/employeeDashboard";
	                 case "ROLE_CLIENT":
	                     return "redirect:/clientDashboard";
	             }
	         }
	     }
	
	     // If no valid role found or error, redirect to login page with error flag
	     return "redirect:/login?error=true";
	 }
	
	 // Handles GET request for the access denied page
	 @GetMapping("/access-denied")
	 public String showAccessDeniedPage(Authentication authentication, Model model) {
	     
	     // If user is authenticated, add their username to the model
	     if (authentication != null && authentication.isAuthenticated()) {
	         model.addAttribute("user", authentication.getName());
	     }
	
	     // Returns the access-denied view
	     return "access-denied";
	 }
	}
