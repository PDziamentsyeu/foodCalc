package main.java.application.web.exceptions;

public class AuthorizationException extends Exception {
	private static final long serialVersionUID = 1L;

	public AuthorizationException(final String message) {
        super(message);
    }

    public AuthorizationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(final Throwable cause) {
        super(cause);
    }
}
