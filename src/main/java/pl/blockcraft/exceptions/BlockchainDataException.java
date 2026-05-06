package pl.blockcraft.exceptions;

public class BlockchainDataException extends Exception {
    public BlockchainDataException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}