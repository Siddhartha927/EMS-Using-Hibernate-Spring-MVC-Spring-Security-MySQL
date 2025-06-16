package com.ems.Service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ems.dao.EmployeeDAO;
import com.ems.dao.ProjectDAO;
import com.ems.entity.Employee;
import com.ems.entity.Project;
import com.ems.entity.Role;
import com.ems.entity.User;

@Service
public class EmployeeService{
		@Autowired
		private PasswordEncoder passwordEncoder;
		@Autowired
	    private EmployeeDAO employeeDAO;
		@Autowired
		private ProjectDAO projectDAO;
    
	    public void addEmployee(Employee employee) {
	        employee.setDateOfJoining(LocalDate.now()); // Set current date
	        employeeDAO.addEmployee(employee);
	    }

	    
	    public Employee getEmployeeByEmail(String email) {
	        return employeeDAO.getEmployeeByEmail(email);
	    }

	   
	    public List<Employee> getAllEmployees() {
	        return employeeDAO.getAllEmployees();
	    }
	    
	    public boolean isEmailExists(String email) {
	    	return employeeDAO.isEmailExists(email);
	    }
	    
	    public Employee getEmployeeById(String employeeId) {
	    	return employeeDAO.getEmployeeById(employeeId);
	    }
	    
	    public void deleteEmployee(String employeeId) {
	    	employeeDAO.deleteEmployeeById(employeeId); 
	    }
	    
	    public void updateEmployee(Employee employee) {
	    	Employee existingEmployee = employeeDAO.getEmployeeById(employee.getEmployeeId());
	    	if(existingEmployee != null) {
	    		existingEmployee.setName(employee.getName());
	    		existingEmployee.setDepartment(employee.getDepartment());
	    		existingEmployee.setPhone(employee.getPhone());
	    		employeeDAO.updateEmployee(existingEmployee);
	    	}
	    	
	    }
	
	    public void assignProject(String employeeId, String projectId) {
			 Employee employee = employeeDAO.getEmployeeById(employeeId);
			 Project project = projectDAO.getProjectById(projectId);
			
			 employee.setProject(project);
			 employeeDAO.updateEmployee(employee);
	        
	    }

	    public void removeProjectFromEmployee(String employeeId) {
	    	Employee employee = employeeDAO.getEmployeeById(employeeId);
	    	employee.setProject(null);
	    	
	    	employeeDAO.updateEmployee(employee);
	    }
	    
	    public void setPassword(String employeeId, String newPassword) {
	    	Employee employee = employeeDAO.getEmployeeById(employeeId);
	    	User user = employee.getUser();
	    	if(user == null) {
	    		user = new User();
	    		user.setEmail(employee.getEmail());	
	    	}
	    	user.setPassword(passwordEncoder.encode(newPassword));
	    	user.setRole(Role.EMPLOYEE);
    		employee.setUser(user);
    		
    		employeeDAO.updateEmployee(employee);
	    	
	    }
}
