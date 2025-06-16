package com.ems.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ems.dao.ProjectDAO;
import com.ems.entity.Client;
import com.ems.entity.Project;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProjectService {

	@Autowired
	private ProjectDAO projectDAO;
	
	@Autowired
	private ClientService clientService;
	

	
	public void addProject(Project project) {
		
		String clientId = project.getClient().getClientId(); 

		Client existingClient = clientService.getClientById(clientId);

		project.setClient(existingClient); 
		projectDAO.addProject(project);
	}
	
	 public Project getProjectById(String projectId) {
		 return projectDAO.getProjectById(projectId);
	 }
	 
	 public void updateProject(Project project) {
		 Project existingProject = projectDAO.getProjectById(project.getProjectId());
	    	if(existingProject != null) {
	    		existingProject.setProjectName(project.getProjectName());
	    		if(project.getUpdatedEndDate() != null) {
	    			existingProject.setUpdatedEndDate(project.getUpdatedEndDate());
	    		}
	    		projectDAO.updateProject(existingProject);
	    	}
	 }
	 
	 public void deleteProject(String projectId) {
	    	projectDAO.deleteProjectById(projectId); 
	    }
	 
	 public List<Project> getAllProjects() {
	        return projectDAO.getAllProjects();
	    }
	 
	 
}
