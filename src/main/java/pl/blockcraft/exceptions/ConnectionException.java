package pl.blockcraft.exceptions;

public class ConnectionException extends Exception{
    public ConnectionException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
