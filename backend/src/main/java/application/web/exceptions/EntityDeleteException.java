package main.java.application.web.exceptions;

public class EntityDeleteException extends Exception {

    private static final long serialVersionUID = 1L;

    public EntityDeleteException(String message) {
        super(message);
    }

    public EntityDeleteException(String message, Exception e) {
        super(message, e);
    }

    public EntityDeleteException(Exception e) {
        super(e);
    }
}
