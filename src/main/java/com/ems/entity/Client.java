package com.ems.entity;


import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;

//Marks this class as a JPA entity representing a database table
@Entity
public class Client {

	// Marks this field as the primary key
	@Id
	// Generates the primary key automatically using an identity column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	// Defines a column that cannot be null and must be unique
	@Column(nullable=false, unique = true)
	String clientId;
	
	// Defines a column that cannot be null
	@Column(nullable=false)
	String clientName;
	
	// Defines a column that stores the relationship date, cannot be null
	@Column(nullable = false)
	private LocalDate relationshipDate;
	
	// Defines a one-to-many relationship with ContactPerson entity
	// The "client" field in ContactPerson maps the relationship
	// CascadeType.ALL ensures changes in Client affect associated ContactPersons
	// orphanRemoval = true removes contact persons if they are removed from the list
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ContactPerson> contactPersons;

	// Getter method for id
	public int getId() {
		return id;
	}

	// Setter method for id
	public void setId(int id) {
		this.id = id;
	}

	// Getter method for clientId
	public String getClientId() {
		return clientId;
	}

	// Setter method for clientId
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	// Getter method for clientName
	public String getClientName() {
		return clientName;
	}

	// Setter method for clientName
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	// Getter method for relationshipDate
	public LocalDate getRelationshipDate() {
		return relationshipDate;
	}

	// Setter method for relationshipDate
	public void setRelationshipDate(LocalDate relationshipDate) {
		this.relationshipDate = relationshipDate;
	}

	// Getter method for contactPersons
	public List<ContactPerson> getContactPersons() {
		return contactPersons;
	}

	// Setter method for contactPersons
	public void setContactPersons(List<ContactPerson> contactPersons) {
		this.contactPersons = contactPersons;
	}

}

