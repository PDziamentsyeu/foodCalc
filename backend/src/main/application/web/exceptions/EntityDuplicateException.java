package main.application.web.exceptions;

public class EntityDuplicateException extends EntityCreateException {

    private static final long serialVersionUID = 1L;

    public EntityDuplicateException(String message) {
        super(message);
    }


}
