package com.ems.dao;

import java.util.List;

import com.ems.entity.Client;
import com.ems.entity.ContactPerson;

	//Defines a DAO interface for Client entity operations
	public interface ClientDAO {
	
	// Saves a client entity to the database
	void saveClient(Client client);

	// Returns the total number of clients
	long countClient();

	// Checks if a client name already exists in the database
	boolean isClientNameExists(String clientName);

	// Retrieves a client by its ID
	Client getClientById(String client);

	// Adds a contact person to a specific client using client ID
	void addContactPerson(ContactPerson contactPerson,String clientId);

	// Retrieves a list of all clients from the database
	List<Client> getAllClients();
	
	// Updates the information of a specific contact person (used in client contact updates)
	public void updateContact(ContactPerson contactPerson);

	// Retrieves a Client object from the database using the client's email address (usually for login/session-related operations)
	Client getClientByEmail(String email);

}

