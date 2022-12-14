package pet.juniors_dev.elibrary.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.internet.AddressException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String emailAlreadyExistsExceptionHandler(EmailAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String constraintViolationExceptionHandler(BindException e) {
        return Objects.requireNonNull(e.getBindingResult()
                .getFieldError())
                .getField()
                + " "
                + e.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
    }

    @ExceptionHandler(AddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String addressExceptionHandler(AddressException e) {
        return e.getMessage();
    }

    @ExceptionHandler(TokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String tokenExceptionHandler(TokenException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notFoundExceptionHandler(NotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalArgumentExceptionHandler(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(FileException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileExceptionHandler(FileException e) {
        return e.getMessage();
    }

    @ExceptionHandler(FileWriteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String fileWriteExceptionHandler(FileWriteException e) {
        return e.getMessage();
    }

    @ExceptionHandler(GCPFileUploadException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String GCPFileUploadExceptionHandler(GCPFileUploadException e) {
        return e.getMessage();
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String dataIntegrityViolationExceptionHandler(DataIntegrityViolationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String notPermittedExceptionHandler(NotPermittedException e) {
        return e.getMessage();
    }
}
