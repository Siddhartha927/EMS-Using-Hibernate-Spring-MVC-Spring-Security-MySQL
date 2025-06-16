package com.ems.daoImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ems.dao.ProjectDAO;
import com.ems.entity.Employee;
import com.ems.entity.Project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
@Repository
@Transactional
public class ProjectDAOImpl implements ProjectDAO{
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void addProject(Project project) {
		project.setProjectId(generateProjectId());
		entityManager.persist(project);
		
	}
	
	@Override
	public long countProject() {
	    return entityManager.createQuery("SELECT COUNT(p) FROM Project p", Long.class)
                .getSingleResult();
	}
	
	private String generateProjectId() {
	        long count = countProject() + 1;
	        return String.format("Project-%03d", count); 
	  }

	@Override
	public Project getProjectById(String projectId) {
		try {
			return entityManager.createQuery("select p from Project p where p.projectId = :projectId",Project.class)
					.setParameter("projectId", projectId)
					.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}

	@Override
	public Project updateProject(Project project) {
			return entityManager.merge(project);
		}

	@Override
	public void deleteProjectById(String projectId) {
		Project project	= entityManager.createQuery(
				"select p from Project p where p.projectId = :projectId",Project.class)
				.setParameter("projectId", projectId)
				.getSingleResult();
		
		if(project != null) {
			entityManager.remove(project);
		}
		
		
		
	}

	@Override
	public List<Project> getAllProjects() {
		 return entityManager.createQuery("SELECT p FROM Project p", Project.class).getResultList();
	}
	
	 @Override
	   public void assignProjectToEmployee(String employeeId, String projectId) {
	        Employee employee = entityManager.find(Employee.class, employeeId);
	        Project project = entityManager.find(Project.class, projectId);

	        if (employee != null && project != null) {
	            employee.setProject(project);
	            project.getEmployees().add(employee);

	            entityManager.merge(employee);
	            entityManager.merge(project);
	        }
	    }
	

}
