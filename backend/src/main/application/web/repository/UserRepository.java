package main.application.web.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import main.application.web.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	//Optional<User> findByUsername(String name);

}
