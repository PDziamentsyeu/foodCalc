package main.java.application.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import main.java.application.web.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
