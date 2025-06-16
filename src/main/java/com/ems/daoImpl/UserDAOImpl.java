package com.ems.daoImpl;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ems.dao.UserDAO;
import com.ems.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO{

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public Optional<User> findByEmail(String email) {
		TypedQuery<User> query = entityManager.createQuery("Select u from User u where u.email=:email", User.class);
		query.setParameter("email",email);
		
		List<User> users = query.getResultList();
		
		return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
	}

	@Override
	@Transactional
	public void save(User user) {
		entityManager.merge(user);
		
	}

	@Override
	public long count() {
		TypedQuery<Long> query = entityManager.createQuery("select count(u) from User u", Long.class);
		return query.getSingleResult();
	}

	
	
	
}
