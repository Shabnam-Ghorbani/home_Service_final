package ir.maktab.home_service.config;

import ir.maktab.home_service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityIsExistException.class)
    public ResponseEntity<Object> entityIsExistExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = EntityNotExistException.class)
    public ResponseEntity<Object> entityNotExistExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = InCorrectException.class)
    public ResponseEntity<Object> inCorrectExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = InvalidEmailException.class)
    public ResponseEntity<Object> invalidEmailExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = InvalidNameException.class)
    public ResponseEntity<Object> invalidNameExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = InvalidNumberException.class)
    public ResponseEntity<Object> invalidNumberExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    public ResponseEntity<Object> invalidPasswordExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notFoundExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = NotMatchException.class)
    public ResponseEntity<Object> notMatchExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = NullException.class)
    public ResponseEntity<Object> nullExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = ScoreException.class)
    public ResponseEntity<Object> scoreExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = ConfirmNotFoundException.class)
    public ResponseEntity<Object> confirmNotFoundExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = DuplicateConfirmException.class)
    public ResponseEntity<Object> duplicateConfirmExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = ImageFormatException.class)
    public ResponseEntity<Object> imageFormatExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = ImageSizeException.class)
    public ResponseEntity<Object> imageSizeExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = InsufficientFoundsException.class)
    public ResponseEntity<Object> insufficientFoundsExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(value = StatusException.class)
    public ResponseEntity<Object> statusExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
