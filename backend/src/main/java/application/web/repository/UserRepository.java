package main.java.application.web.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import main.java.application.web.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findById(Long id);

}
