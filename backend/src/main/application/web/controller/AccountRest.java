package main.application.web.controller;

import java.net.URI;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.application.web.exceptions.EntityDeleteException;
import main.application.web.model.Account;
import main.application.web.model.User;
import main.application.web.repository.AccountRepository;
import main.application.web.repository.UserRepository;

@RestController
@RequestMapping(path = "/accounts")
public class AccountRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRest.class);
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    /*--------------------------------Create operation ----------------------------*/
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Account forCreate) {
        LOGGER.info("create new account");
        LOGGER.info("create account with data: "+forCreate.toString());
        User userInfo = new User();
        userInfo = userRepository.save(userInfo);
        forCreate.setUserDetail(userInfo);
        Account result = accountRepository.save(forCreate);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        LOGGER.info("creating of new account completed");
        return ResponseEntity.created(location).build();

    }
    
    /*--------------------------------Single operation ----------------------------*/
    @SuppressWarnings("unchecked")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getAccount(@PathVariable("id") long id) {
        LOGGER.info("Fetching Account with id {}", id);
        Account account = accountRepository.findById(id);
        if (account == null) {
            LOGGER.error("Account with id {} not found.", id);
            return new ResponseEntity(new EntityNotFoundException("Account with id " + id 
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Account>(account, HttpStatus.OK);
    }
    
    /*--------------------------------Delete operation ----------------------------*/
    @SuppressWarnings("unchecked")
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete(@PathVariable("id") Long id) {
        LOGGER.info("Fetching & Deleting Account with id {}", id);
        Account account = accountRepository.findById(id);
        if (account == null) {
            LOGGER.error("Unable to delete. Account with id {} not found.", id);
            return new ResponseEntity(new EntityDeleteException("Unable to delete. Account with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        accountRepository.delete(account);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        
    }
    
    /*--------------------------------Retrieve all ----------------------------*/
    @GetMapping()
    public @ResponseBody Iterable<Account> listAllAccounts() {
        return accountRepository.findAll();
    }

    
    

}
