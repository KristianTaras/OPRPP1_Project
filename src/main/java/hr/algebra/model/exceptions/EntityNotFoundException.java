package hr.algebra.model.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
