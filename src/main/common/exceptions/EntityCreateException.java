package main.common.exceptions;


public class EntityCreateException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityCreateException(String message) {
        super(message);
    }

    public EntityCreateException(String message, Exception e) {
        super(message, e);
    }

    public EntityCreateException(Exception e) {
        super(e);
    }

}
