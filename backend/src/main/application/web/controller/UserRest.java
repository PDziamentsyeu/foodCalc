package main.application.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import main.application.web.model.User;
import main.application.web.repository.UserRepository;

@RestController
@RequestMapping(path = "/users")
public class UserRest {

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = Logger.getLogger(UserRest.class);

    @GetMapping()
    public @ResponseBody Iterable<User> listAllUsers() {
    	LOGGER.info("get all users");
        return userRepository.findAll();
    }

}
