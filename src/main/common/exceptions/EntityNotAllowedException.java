package main.common.exceptions;

public class EntityNotAllowedException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityNotAllowedException(String message) {
        super(message);
    }

    public EntityNotAllowedException(String message, Exception e) {
        super(message, e);
    }

    public EntityNotAllowedException(Exception e) {
        super(e);
    }

}
