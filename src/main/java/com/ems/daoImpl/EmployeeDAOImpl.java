package com.ems.daoImpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ems.dao.EmployeeDAO;
import com.ems.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
@Repository
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {
	  @PersistenceContext
	    private EntityManager entityManager;

	    @Override
	    public void addEmployee(Employee employee) {
	        employee.setEmployeeId(generateEmployeeId());
	        entityManager.persist(employee);
	    }

	    @Override
	    public long countEmployees() {
	        return entityManager.createQuery("SELECT COUNT(e) FROM Employee e", Long.class)
	                            .getSingleResult();
	    }

	    @Override
	    public Employee getEmployeeByEmail(String email) {
	        try {
	            return entityManager.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
	                                .setParameter("email", email)
	                                .getSingleResult();
	        } catch (Exception e) {
	            return null;
	        }
	    }

	    @Override
	    public List<Employee> getAllEmployees() {
	        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
	    }

	    private String generateEmployeeId() {
	        long count = countEmployees() + 1;
	        return String.format("JTC-%03d", count); 
	    }

		@Override
		public boolean isEmailExists(String email) {
			try {
				String sql = "select e from Employee e where e.email = :email";
				Employee	employee =entityManager.createQuery(sql,Employee.class)
				.setParameter("email", email)
				.setMaxResults(1)
				.getSingleResult();
				return employee != null;
			}catch(NoResultException e){
				return false;
			}
			
		}

		@Override
		public Employee getEmployeeById(String employeeId) {
		try {
			return entityManager.createQuery("select e from Employee e where e.employeeId = :employeeId",Employee.class)
					.setParameter("employeeId", employeeId)
					.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
		}

		@Override
		public void deleteEmployeeById(String employeeId) {
		Employee employee	= entityManager.createQuery(
				"select e from Employee e where e.employeeId = :employeeId",Employee.class)
				.setParameter("employeeId", employeeId)
				.getSingleResult();
		
		if(employee != null) {
			entityManager.remove(employee);
		}
		}

		@Override
		public Employee updateEmployee(Employee employee) {
			return entityManager.merge(employee);
			
		}
}
