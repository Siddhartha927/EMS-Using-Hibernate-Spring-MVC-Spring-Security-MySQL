package com.ems.entity;



import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(nullable=false,unique = true)
	String projectId;
	
	@Column(nullable=false)
	String projectName;
	
	@Column(nullable=false)
	LocalDate startDate = LocalDate.now();
	
	@Column(nullable=false)
	LocalDate endDate;
	
	
	LocalDate updatedEndDate;
	
	@ManyToOne  
    @JoinColumn(name = "clientId",referencedColumnName = "clientId", nullable = false)
	Client client;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
	List<Employee> employees;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalDate getUpdatedEndDate() {
		return updatedEndDate;
	}

	public void setUpdatedEndDate(LocalDate updatedEndDate) {
		this.updatedEndDate = updatedEndDate;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	
	
	
}
