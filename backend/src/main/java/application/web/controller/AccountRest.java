package main.java.application.web.controller;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import main.java.application.web.exceptions.AuthorizationException;
import main.java.application.web.exceptions.EntityDeleteException;
import main.java.application.web.exceptions.UserConflictException;
import main.java.application.web.exceptions.UserNotExistsException;
import main.java.application.web.model.Account;
import main.java.application.web.model.Role;
import main.java.application.web.model.User;
import main.java.application.web.model.dto.AccountUpdateDto;
import main.java.application.web.model.dto.LoginDto;
import main.java.application.web.model.dto.LoginRequestDto;
import main.java.application.web.repository.AccountRepository;
import main.java.application.web.repository.RoleRepository;
import main.java.application.web.repository.UserRepository;
import main.java.application.web.security.jwt.JwtAuth;
import main.java.application.web.security.jwt.JwtParseClaimsException;
import main.java.application.web.security.jwt.JwtService;
import main.java.application.web.security.jwt.PasswordUtils;
import main.java.application.web.security.jwt.model.UserClaims;
import main.java.application.web.service.AccountService;

@RestController
@RequestMapping(path = "/accounts")
public class AccountRest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRest.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	/*--------------------------------Login operation ----------------------------*/
	@PostMapping("/login")
	public ResponseEntity<LoginRequestDto> login(@RequestBody @Valid final LoginDto loginForm)
			throws UserNotExistsException, AuthorizationException {
		final Account account = accountService.checkPasswordAndGetUser(loginForm.getEmail(), loginForm.getPassword());
		String token = JwtService.generateToken(account.getEmail(), account.getId(), account.getRole().getRoleName());
		new ResponseEntity<LoginRequestDto>(new LoginRequestDto(token), HttpStatus.OK);
		return null;
	}

	/*--------------------------------Create operation ----------------------------*/
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Account forCreate) throws AuthorizationException {
		LOGGER.info("create account with data: " + forCreate.toString());
		User userInfo = new User();
		userInfo = userRepository.save(userInfo);
		Role role = roleRepository.findByRoleName(forCreate.getRole().getRoleName());
		LOGGER.info("role "+role.toString());
		forCreate.setRole(role);
		LOGGER.info("not encrypted "+forCreate.getPassword());
		forCreate.setUserDetail(userInfo);
		forCreate.setPassword(PasswordUtils.encryptPassword(forCreate.getPassword()));
		LOGGER.info("encrypted "+forCreate.getPassword());
		Account result = accountRepository.save(forCreate);
		LOGGER.info("creating of new account completed");
		return new ResponseEntity<Account>(result, HttpStatus.OK);

	}

	@JwtAuth("ADMIN")
	@PutMapping
	public ResponseEntity<Void> changePassword(@RequestBody @Valid final AccountUpdateDto accountUpdateDto,
			final HttpServletRequest request)
			throws JwtParseClaimsException, UserNotExistsException, AuthorizationException, UserConflictException {
		final UserClaims claims = JwtService.getUserClaims(request);
		accountService.updateAccount(claims.getId(), accountUpdateDto.getEmail(), accountUpdateDto.getPassword(),
				accountUpdateDto.getNewPassword());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/*--------------------------------Single operation ----------------------------*/
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getAccount(@PathVariable("id") long id) {
		LOGGER.info("Fetching Account with id {}", id);
		Account account = accountRepository.findById(id);
		if (account == null) {
			LOGGER.error("Account with id {} not found.", id);
			return new ResponseEntity(new EntityNotFoundException("Account with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	/*--------------------------------Delete operation ----------------------------*/
	@SuppressWarnings("unchecked")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		LOGGER.info("Fetching & Deleting Account with id {}", id);
		Account account = accountRepository.findById(id);
		if (account == null) {
			LOGGER.error("Unable to delete. Account with id {} not found.", id);
			return new ResponseEntity(
					new EntityDeleteException("Unable to delete. Account with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		accountRepository.delete(account);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);

	}

	/*--------------------------------Retrieve all ----------------------------*/
	@JwtAuth("ADMIN")
	@GetMapping()
	public @ResponseBody Iterable<Account> listAllAccounts() {
		return accountRepository.findAll();
	}

	/*--------------------------------Userifo operations ----------------------------*/
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/{id}/user")
	public ResponseEntity<?> getUserInfo(@PathVariable("id") long id) {
		LOGGER.info("Requesting UserDetails for Account with id {}", id);
		Account account = accountRepository.findById(id);
		if (account == null) {
			LOGGER.error("Account with id {} not found.", id);
			return new ResponseEntity(new EntityNotFoundException("Account with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		User userInfo = userRepository.findById(account.getUserDetail().getId());
		return new ResponseEntity<User>(userInfo, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/{id}/user")
	public ResponseEntity<?> updateUserInfo(@PathVariable("id") long id, @RequestBody User forUpdate) {
		LOGGER.info("Updating UserDetails for Account with id {}", id);
		Account account = accountRepository.findById(id);
		if (account == null) {
			LOGGER.error("Account with id {} not found.", id);
			return new ResponseEntity(new EntityNotFoundException("Account with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		User userInfo = userRepository.findById(account.getUserDetail().getId());
		userInfo = userRepository.save(forUpdate);
		account.setUserDetail(userInfo);
		account = accountRepository.save(account);
		LOGGER.info("update for account complete ");
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	/*--------------------------------Exception handling ----------------------------*/
	@ExceptionHandler({ UserNotExistsException.class, AuthorizationException.class, JwtParseClaimsException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public void handleAuthorizationExceptions(final Exception exception) {
		LOGGER.info(exception.getMessage());
	}

	@ExceptionHandler(UserConflictException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public void handleConflicts(final UserConflictException exception) {
		LOGGER.info(exception.getMessage());
	}

}
