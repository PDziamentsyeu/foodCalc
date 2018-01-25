package main.application.web.controller;

import java.net.URI;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import main.application.web.exceptions.EntityCreateException;
import main.application.web.model.User;
import main.application.web.repository.UserRepository;

@RestController
@RequestMapping(path = "/users")
public class UserRest {

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger(UserRest.class);
    
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody User forCreate) {
        LOGGER.info("create new user");
       // validateUser(forCreate.getId());
        User result = userRepository.save(forCreate);
        URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(result.getId()).toUri();
        LOGGER.info("creating of new user completed");
        return ResponseEntity.created(location).build();

    }

    @GetMapping("/all")
    public  @ResponseBody Iterable<User> getAll() {
        return userRepository.findAll();
    }
    
    private void validateUser(Long userId) {
		this.userRepository.findById(userId).orElseThrow(
				() -> new EntityCreateException("User with id {} not found:"+userId));
	}

}
