package pet.juniors_dev.elibrary.exception;

public class FileWriteException extends RuntimeException {
    public FileWriteException() {
    }

    public FileWriteException(String message) {
        super(message);
    }
}
