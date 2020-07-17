package services;

/**
 * Exception used by to say user with same login already exists
 */
public class SameUserLoginException extends Exception{
    public SameUserLoginException() {
        super();
    }

    public SameUserLoginException(String message) {
        super(message);
    }
}
