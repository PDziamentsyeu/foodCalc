package main.service;

import java.util.List;

import main.java.User;

public interface UserService {
	
	User findById(Long id);
    
    User findByName(String name);
     
    void saveUser(User user);
     
    void updateUser(User user);
     
    void deleteUserById(long id);
 
    List<User> findAllUsers();
     
    void deleteAllUsers();
     
    boolean isUserExist(User user);

}
