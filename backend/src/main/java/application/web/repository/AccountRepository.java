package main.java.application.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import main.java.application.web.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findById(Long id);
    Account findByEmail(String email);

}
