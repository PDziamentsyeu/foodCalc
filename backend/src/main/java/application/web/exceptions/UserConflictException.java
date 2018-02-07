package main.java.application.web.exceptions;

public class UserConflictException extends Exception {
    public UserConflictException(final String message) {
        super(message);
    }

    public UserConflictException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserConflictException(final Throwable cause) {
        super(cause);
    }
}
