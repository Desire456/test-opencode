package utils.exceptions;

/**
 * Exception used by to say about some problems with hashing
 */
public class HashPasswordException extends RuntimeException{
    public HashPasswordException() {
        super();
    }

    public HashPasswordException(String message) {
        super(message);
    }
}
