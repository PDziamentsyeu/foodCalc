package main.java.application.web.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.java.application.web.exceptions.AuthorizationException;
import main.java.application.web.exceptions.EntityCreateException;
import main.java.application.web.exceptions.UserConflictException;
import main.java.application.web.exceptions.UserNotExistsException;
import main.java.application.web.model.Account;
import main.java.application.web.model.Role;
import main.java.application.web.model.User;
import main.java.application.web.repository.AccountRepository;
import main.java.application.web.repository.RoleRepository;
import main.java.application.web.repository.UserRepository;
import main.java.application.web.security.jwt.PasswordUtils;

@Service
public class AccountService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountService.class);
	private static final String USER_ROLE="USER";
	private static final String ADMIN_ROLE="ADMIN";
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Transactional
	public Account checkPasswordAndGetUser(final String email, final String password)
			throws UserNotExistsException, AuthorizationException {
		final Account account = accountRepository.findByEmail(email);
		if (account == null) {
			throw new UserNotExistsException(String.format("User [%s] does not exist", email));
		}
		if (!account.getPassword().equals(PasswordUtils.encryptPassword(password))) {
			throw new AuthorizationException("Passwords does not matches");
		}
		return account;
	}

	@Transactional
	public Account updateAccount(final long id, final String email, final String currentPassword,
			final String newPassword) throws UserNotExistsException, AuthorizationException, UserConflictException {
		final Account accountForCheck = accountRepository.findByEmail(email);
		if (accountForCheck != null && accountForCheck.getId() != id) {
			throw new UserConflictException(String.format("User with name [%s] already exists", email));
		}
		final Account account = accountRepository.findOne(id);
		if (account == null) {
			throw new UserNotExistsException(String.format("User [%s] does not exist", id));
		}
		if (newPassword != null) {
			if (!account.getPassword().equals(PasswordUtils.encryptPassword(currentPassword))) {
				throw new AuthorizationException("Passwords does not matches");
			}
			account.setPassword(PasswordUtils.encryptPassword(newPassword));
		}
		if (email != null) {
			account.setEmail(email);
		}
		return accountRepository.save(account);
	}
	
	@Transactional
	public Account checkAndCreateUser(Account forCreate) throws AuthorizationException{
			
		final Account account = accountRepository.findByEmail(forCreate.getEmail());
		if (account == null) {
			User userInfo = new User();
			userInfo = userRepository.save(userInfo);
			Role role = roleRepository.findByRoleName(USER_ROLE);
			forCreate.setRole(role);
			forCreate.setUserDetail(userInfo);
			forCreate.setPassword(PasswordUtils.encryptPassword(forCreate.getPassword()));
		} else {
			throw new EntityCreateException(forCreate.getEmail()+" exists");
		}
		
		return accountRepository.save(forCreate);
	}
	
	@Transactional
	public Account checkAndCreateAdmin(Account forCreate) throws AuthorizationException{
			
		final Account account = accountRepository.findByEmail(forCreate.getEmail());
		if (account == null) {
			User userInfo = new User();
			userInfo = userRepository.save(userInfo);
			Role role = roleRepository.findByRoleName(ADMIN_ROLE);
			forCreate.setRole(role);
			forCreate.setUserDetail(userInfo);
			forCreate.setPassword(PasswordUtils.encryptPassword(forCreate.getPassword()));
		} else {
			throw new EntityCreateException(forCreate.getEmail()+" exists");
		}
		
		return accountRepository.save(forCreate);
	}

}
