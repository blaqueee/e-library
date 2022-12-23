package pet.juniors_dev.elibrary.exception;

public class NotPermittedException extends RuntimeException {
    public NotPermittedException() {
    }

    public NotPermittedException(String message) {
        super(message);
    }
}
