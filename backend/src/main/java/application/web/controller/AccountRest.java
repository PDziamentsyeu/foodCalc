package main.java.application.web.controller;

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
import main.java.application.web.exceptions.EntityCreateException;
import main.java.application.web.exceptions.EntityDeleteException;
import main.java.application.web.exceptions.EntityDuplicateException;
import main.java.application.web.exceptions.EntityExistsException;
import main.java.application.web.exceptions.EntityNotFoundException;
import main.java.application.web.model.Account;
import main.java.application.web.model.User;
import main.java.application.web.model.dto.AccountUpdateDto;
import main.java.application.web.model.dto.LoginDto;
import main.java.application.web.model.dto.LoginRequestDto;
import main.java.application.web.repository.AccountRepository;
import main.java.application.web.repository.UserRepository;
import main.java.application.web.security.jwt.JwtAuth;
import main.java.application.web.security.jwt.JwtParseClaimsException;
import main.java.application.web.security.jwt.JwtService;
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

	/*--------------------------------Login operation ----------------------------*/
	@PostMapping("/login")
	public ResponseEntity<LoginRequestDto> login(@RequestBody @Valid final LoginDto loginForm)
			throws AuthorizationException, EntityNotFoundException {
		final Account account = accountService.checkPasswordAndGetUser(loginForm.getEmail(), loginForm.getPassword());
		String token = JwtService.generateToken(account.getEmail(), account.getId(), account.getRole().getRoleName());
		return new ResponseEntity<LoginRequestDto>(new LoginRequestDto(token), HttpStatus.OK);
	}

	/*--------------------------------Create operation ----------------------------*/
	@PostMapping("/account")
	public ResponseEntity<Account> create(@RequestBody Account forCreate) throws AuthorizationException {
		LOGGER.info("create account with data: " + forCreate.getEmail());
		Account result = accountService.checkAndCreateUser(forCreate);
		return new ResponseEntity<Account>(result, HttpStatus.OK);

	}

	@PostMapping("/account/admin")
	public ResponseEntity<Account> createAdmin(@RequestBody Account forCreate) throws AuthorizationException {
		LOGGER.info("create account with data: " + forCreate.toString());
		Account result = accountService.checkAndCreateAdmin(forCreate);
		return new ResponseEntity<Account>(result, HttpStatus.OK);

	}

	@JwtAuth(value = { "ADMIN", "USER" })
	@PutMapping("/account")
	public ResponseEntity<?> changePasswordOrEmail(@RequestBody final AccountUpdateDto accountUpdateDto,
			final HttpServletRequest request) throws JwtParseClaimsException, AuthorizationException {
		final UserClaims claims = JwtService.getUserClaims(request);
		Account result = null;
		try {
			result = accountService.updateAccount(claims.getId(), accountUpdateDto.getEmail(),
					accountUpdateDto.getPassword(), accountUpdateDto.getNewPassword());
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Account>(result, HttpStatus.OK);
	}

	/*--------------------------------Single operation ----------------------------*/
	@JwtAuth(value = { "ADMIN", "USER" })
	@GetMapping("/account")
	public ResponseEntity<?> getAccount(final HttpServletRequest request) throws JwtParseClaimsException {
		Long id = JwtService.getUserClaims(request).getId();
		LOGGER.info("Fetching Account with id {}", id);
		Account account = accountRepository.findById(id);
		if (account == null) {
			LOGGER.error("Account with id {} not found.", id);
			return new ResponseEntity<>(new EntityNotFoundException("Account with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	/*--------------------------------Delete operation ----------------------------*/
	@JwtAuth("ADMIN")
	@DeleteMapping("/account/{id}")
	public ResponseEntity<?> delete(final HttpServletRequest request, @PathVariable("id") long id)
			throws JwtParseClaimsException {
		LOGGER.info("Fetching & Deleting Account with id {}", id);
		Account account = accountRepository.findById(id);
		if (account == null) {
			LOGGER.error("Unable to delete. Account with id {} not found.", id);
			return new ResponseEntity<>(
					new EntityDeleteException("Unable to delete. Account with id " + id + " not found."),
					HttpStatus.NOT_FOUND);
		}
		accountRepository.delete(account);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);

	}

	/*--------------------------------Retrieve all ----------------------------*/
	@JwtAuth(value = { "ADMIN" })
	@GetMapping()
	public @ResponseBody Iterable<Account> listAllAccounts() {
		return accountRepository.findAll();
	}

	/*--------------------------------Userifo operations ----------------------------*/
	@JwtAuth(value = { "ADMIN", "USER" })
	@GetMapping(value = "/account/user")
	public ResponseEntity<?> getUserInfo(final HttpServletRequest request) throws JwtParseClaimsException {
		Long id = JwtService.getUserClaims(request).getId();
		LOGGER.info("Requesting UserDetails for Account with id {}", id);
		Account account = accountRepository.findById(id);
		if (account == null) {
			LOGGER.error("Account with id {} not found.", id);
			return new ResponseEntity<>(new EntityNotFoundException("Account with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		User userInfo = userRepository.findById(account.getUserDetail().getId());
		return new ResponseEntity<User>(userInfo, HttpStatus.OK);
	}

	@JwtAuth(value = { "ADMIN", "USER" })
	@PostMapping(value = "/account/user")
	public ResponseEntity<?> updateUserInfo(final HttpServletRequest request, @RequestBody User forUpdate)
			throws JwtParseClaimsException {
		Long id = JwtService.getUserClaims(request).getId();
		LOGGER.info("Updating UserDetails for Account with id {}", id);
		Account account = accountRepository.findById(id);
		User userInfo = userRepository.findById(account.getUserDetail().getId());
		userInfo = userRepository.save(forUpdate);
		account.setUserDetail(userInfo);
		account = accountRepository.save(account);
		LOGGER.info("update for account complete ");
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	/*--------------------------------Exception handling ----------------------------*/
	@ExceptionHandler({ AuthorizationException.class, JwtParseClaimsException.class })
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<?> handleAuthorizationExceptions(final Exception exception) {
		LOGGER.info(exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(),
				HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ EntityDuplicateException.class, EntityExistsException.class, EntityCreateException.class })
	@ResponseStatus(HttpStatus.CONFLICT)
	public  ResponseEntity<?> handleConflicts(final RuntimeException exception) {
		LOGGER.info(exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(),
				HttpStatus.CONFLICT);
	} 

}
