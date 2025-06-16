package com.ems.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;


import com.ems.dao.ClientDAO;
import com.ems.entity.Client;
import com.ems.entity.ContactPerson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

//Marks this class as a Spring Repository (DAO implementation)
@Repository
public class ClientDAOImpl implements ClientDAO {
	
	// Injects the JPA EntityManager for interacting with the database
	@PersistenceContext
	private EntityManager entityManager;
	
	// Saves a client entity to the database
	@Override
	@Transactional
	public void saveClient(Client client) {
		client.setClientId(generateClientId()); // Auto-generates client ID
		entityManager.persist(client); // Persists the client entity
	}

	// Counts total number of clients in the database
	@Override
	public long countClient() {
	    return entityManager.createQuery("SELECT COUNT(c) FROM Client c", Long.class)
             .getSingleResult(); // Executes JPQL query to count clients
	}
	
	// Generates a formatted client ID based on the count
	private String generateClientId() {
	        long count = countClient() + 1; // Increments existing count
	        return String.format("Client-%03d", count); // Format: Client-001, Client-002, etc.
	}
	
	// Checks if a client name already exists in the database
	@Override
	public boolean isClientNameExists(String clientName) {
		try {
			String sql = "select c from Client c where c.clientName = :clientName"; // JPQL query
			Client	client =entityManager.createQuery(sql,Client.class) // Executes query
			.setParameter("clientName", clientName) // Sets query parameter
			.setMaxResults(1) // Limits result to 1
			.getSingleResult(); // Gets single result
			return client != null; // Returns true if client exists
		}catch(NoResultException e){
			return false; // Returns false if no result is found
		}
	}

	// Retrieves a client by its ID, including associated contact persons
	@Override
	public Client getClientById(String clientId) {
		try {
		String jpql = "select c from Client c LEFT JOIN FETCH c.contactPersons where c.clientId = :clientId"; // JPQL with fetch join
		return entityManager.createQuery(jpql,Client.class) // Executes query
				.setParameter("clientId", clientId) // Sets clientId parameter
				.getSingleResult(); // Returns the client
	}catch(NoResultException e) {
		return null; // Returns null if client not found
	}
	}

	// Adds a contact person to a client by client ID
	@Override
	public void addContactPerson(ContactPerson contactPerson, String clientId) {
	   
	    try {
	    String jpql = "SELECT c FROM Client c WHERE c.clientId = :clientId"; // JPQL query to find client
	    Client client = entityManager.createQuery(jpql, Client.class) // Executes query
	                                 .setParameter("clientId", clientId) // Sets client ID
	                                 .getSingleResult(); // Gets the client

	        contactPerson.setClient(client); // Sets the client reference
	        entityManager.persist(contactPerson); // Persists the contact person
	    }catch(NoResultException e) {
	    	throw new RuntimeException("Client not found for ID : " + clientId); // Throws exception if client not found
	    }
	}

	// Retrieves all clients from the database
	@Override
	public List<Client> getAllClients() {
		return entityManager.createQuery("select c from Client c", Client.class) // JPQL to fetch all clients
				.getResultList(); // Returns list of clients
	}

	@Override
	public void updateContact(ContactPerson contactPerson) {
		entityManager.merge(contactPerson);
		
	}

	@Override
	public Client getClientByEmail(String email) {
		return entityManager.createQuery("select client from ContactPerson cp where cp.email = :email",Client.class)
				 .setParameter("email", email)
				 .getSingleResult();
	}

}

