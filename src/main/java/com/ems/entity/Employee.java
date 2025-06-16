package com.ems.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
@Entity
@Table(name= "employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(nullable=false,unique = true)
	String employeeId;
	@Column(nullable=false)
	String name;
	@Column(nullable=false)
	String department;
	@Column(nullable=false,unique = true)
	String email;
	@Column(nullable=false)
	String phone;
	
	@Column(nullable=false)
	LocalDate DateOfJoining = LocalDate.now();
	@ManyToOne  
    @JoinColumn(name = "projectId", referencedColumnName = "projectId", nullable = true)
	Project project;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;
	
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public LocalDate getDateOfJoining() {
		return DateOfJoining;
	}
	public void setDateOfJoining(LocalDate dateOfJoining) {
		DateOfJoining = dateOfJoining;
	}
	
	
	
}
