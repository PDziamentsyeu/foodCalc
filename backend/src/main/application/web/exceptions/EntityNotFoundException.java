package main.application.web.exceptions;

public class EntityNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Exception e) {
        super(message, e);
    }

    public EntityNotFoundException(Exception e) {
        super(e);
    }
}
