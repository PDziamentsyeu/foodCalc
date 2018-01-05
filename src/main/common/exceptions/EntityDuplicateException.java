package main.common.exceptions;

public class EntityDuplicateException extends EntityCreateException {

    private static final long serialVersionUID = 1L;

    public EntityDuplicateException(String message) {
        super(message);
    }

    public EntityDuplicateException(String message, Exception e) {
        super(message, e);
    }

    public EntityDuplicateException(Exception e) {
        super(e);
    }

}
