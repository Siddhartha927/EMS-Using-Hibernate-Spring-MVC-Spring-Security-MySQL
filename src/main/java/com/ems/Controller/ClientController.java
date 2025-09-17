package com.ems.Controller;

import com.ems.Service.ClientService;
import com.ems.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//Marks this class as a Spring MVC Controller
@Controller

//Maps all requests starting with "/clientDashboard" to this controller
@RequestMapping("/clientDashboard")
public class ClientController {

 // Automatically injects an instance of ClientService
 @Autowired
 private ClientService clientService;

 // Handles HTTP GET requests to "/clientDashboard"
 @GetMapping
 public String getClientDashboardName(Model model) {

     // Retrieves the current authentication object from the security context
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

     // Extracts the email (username) of the currently logged-in client
     String email = authentication.getName();

     // Fetches the Client object associated with the email
     Client client = clientService.getClientByEmail(email);

     // Adds the client object to the model to be accessed in the view
     model.addAttribute("client", client);

     // Returns the name of the Thymeleaf view to render (clientDashboard.html)
     return "clientDashboard";
 }
 
 public String dummy() {
	 return null;
 }
}

