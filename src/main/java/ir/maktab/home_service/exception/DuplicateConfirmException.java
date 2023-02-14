package ir.maktab.home_service.exception;

public class DuplicateConfirmException extends RuntimeException {
    public DuplicateConfirmException(String message) {
        super(message);
    }
}
