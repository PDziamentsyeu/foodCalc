package main.java.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import main.java.User;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User,Long> {

}
