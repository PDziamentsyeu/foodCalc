package main.application.web.exceptions;


public class EntityUpdateException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityUpdateException(String message) {
        super(message);
    }

    public EntityUpdateException(String message, Exception e) {
        super(message, e);
    }

    public EntityUpdateException(Exception e) {
        super(e);
    }

}
