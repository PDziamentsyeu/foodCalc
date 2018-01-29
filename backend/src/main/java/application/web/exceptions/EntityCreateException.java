package main.java.application.web.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityCreateException extends RuntimeException {
	
	private static final long serialVersionUID = -218398366618725495L;

	public EntityCreateException(String userId) {
			super("could not find user '" + userId + "'.");
		}

}
