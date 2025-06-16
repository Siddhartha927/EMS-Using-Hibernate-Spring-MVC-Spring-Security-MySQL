package com.ems.entity;

import jakarta.persistence.*;

@Entity
public class ContactPerson {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(nullable=false)
	String name;
	
	@Column(nullable=false,unique = true)
	String email;
	
	@Column(nullable=false)
	String phone;
	
	@Column(nullable=false)
	String designation;
	
	@ManyToOne
	@JoinColumn(name="clientId" , referencedColumnName = "clientId", nullable=false)
	private Client client;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	

	
	
}
