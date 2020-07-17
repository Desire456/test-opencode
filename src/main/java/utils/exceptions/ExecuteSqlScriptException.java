package utils.exceptions;

/**
 * Exception used by to say about some problems with executing sql start script
 */
public class ExecuteSqlScriptException extends Exception{
    public ExecuteSqlScriptException() {
        super();
    }

    public ExecuteSqlScriptException(String message) {
        super(message);
    }
}
