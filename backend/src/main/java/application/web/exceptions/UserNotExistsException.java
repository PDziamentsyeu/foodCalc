package main.java.application.web.exceptions;

public class UserNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserNotExistsException(final String message) {
        super(message);
    }

    public UserNotExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserNotExistsException(final Throwable cause) {
        super(cause);
    }
}
