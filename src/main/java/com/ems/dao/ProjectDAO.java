package com.ems.dao;

import java.util.List;

import com.ems.entity.Project;

//Defines a DAO interface for Project entity operations
public interface ProjectDAO {

 // Adds a new project to the database
	void addProject(Project project);

	// Returns the total number of projects
	long countProject();

	// Retrieves a project by its unique project ID
	Project getProjectById(String projectId);

	// Updates the details of an existing project
	Project updateProject(Project project);

	// Deletes a project by its ID
	void deleteProjectById(String projectId);

	// Retrieves a list of all projects from the database
	List<Project> getAllProjects();

	// Assigns a project to an employee using employee and project IDs
	void assignProjectToEmployee(String employeeId, String projectId);
}

