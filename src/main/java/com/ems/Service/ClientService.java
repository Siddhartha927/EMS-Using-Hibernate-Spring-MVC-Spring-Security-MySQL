package com.ems.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ems.dao.ClientDAO;
import com.ems.entity.Client;
import com.ems.entity.ContactPerson;
import com.ems.entity.Role;
import com.ems.entity.User;
import jakarta.transaction.Transactional;
	//Marks this class as a Spring service component
	@Service
	
	//Ensures that methods execute within a transactional context
	@Transactional
	public class ClientService {
	 
	@Autowired
	PasswordEncoder passwordEncoder;
	 // Injects an instance of ClientDAO to interact with the database
	 @Autowired
	 private ClientDAO clientDAO;
	 
	 // Method to add a new client to the database
	 public void addClient(Client client) {
	     client.setRelationshipDate(LocalDate.now()); // Sets the current date as the relationship start date
	     clientDAO.saveClient(client); // Calls DAO method to save the client
	 }
	 
	 // Method to check if a client name already exists in the database
	 public boolean isClientNameExists(String clientName) {
	     return clientDAO.isClientNameExists(clientName);
	 }
	 
	 // Method to retrieve a client by their ID
	 public Client getClientById(String clientId) {
	     return clientDAO.getClientById(clientId);
	 }
	
	 // Method to add a contact person for a specific client
	 public void addContactPerson(ContactPerson contactPerson, String clientId) {
	     clientDAO.addContactPerson(contactPerson, clientId);
	 }
	 
	 // Method to retrieve all clients from the database
	 public List<Client> getAllClients() {
	     return clientDAO.getAllClients();
	 }
	 
	  public String setPassword(String clientId, String newPassword) {
		  
		  		Client client = clientDAO.getClientById(clientId);
		  		List<ContactPerson> contactList = client.getContactPersons();
		  		if(contactList != null && !contactList.isEmpty()) {
		  			for(ContactPerson contact : contactList) {
		  				User user = contact.getUser();
		  				if(user == null) {
		  					user = new User();
		  					user.setEmail(contact.getEmail());
		  				}
		  				user.setPassword(passwordEncoder.encode(newPassword));
		  				user.setRole(Role.CLIENT);
		  				contact.setUser(user);
		  				clientDAO.updateContact(contact);
		  			}
		  		}else {
		  			return "no contacts";
		  		}
		  		return "password saved";
	    }
	  
		  public Client getClientByEmail(String email) {
			 return clientDAO.getClientByEmail(email);
		  }
	}

