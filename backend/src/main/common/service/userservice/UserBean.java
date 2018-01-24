package main.common.service.userservice;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import main.common.EntityManagerCRUDBean;
import main.common.exceptions.EntityCreateException;
import main.common.exceptions.EntityNotAllowedException;
import main.common.exceptions.EntityNotFoundException;
import main.java.model.User;



@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserBean extends EntityManagerCRUDBean<User> {
    

	public User getByEmail(String email) throws EntityNotFoundException, EntityNotAllowedException {
		User user = null;
		try {
			Query query = getEntityManager()
					.createNativeQuery("SELECT e from MneUser e WHERE LOWER(e.email)=:email", User.class);
			query.setParameter("email", email.toLowerCase());
			user = getSingleResult(query);
		} catch (NoResultException e) {
			throw new EntityNotFoundException(e);
		} catch (NonUniqueResultException e) {
			throw new EntityNotFoundException(e);
		} catch (Exception e) {
			throw new EntityNotFoundException(e);
		}
		if (user == null) {
			throw new EntityNotFoundException("User not found");
		}
		return user;
	}

	public User findOrCreate(String email) throws EntityNotAllowedException, EntityCreateException {
		User user;
		try {
			user = getByEmail(email);
		} catch (EntityNotFoundException e) {
			user = getInstance();
			user.setEmail(email);
			create(user);
		}
		return user;
	}

	

}
