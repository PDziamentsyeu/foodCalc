package main.application.web.exceptions;


public class EntityInvalidException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityInvalidException(String message) {
        super(message);
    }

    public EntityInvalidException(String message, Exception e) {
        super(message, e);
    }

    public EntityInvalidException(Exception e) {
        super(e);
    }

}
