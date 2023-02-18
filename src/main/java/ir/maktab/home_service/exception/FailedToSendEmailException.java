package ir.maktab.home_service.exception;

public class FailedToSendEmailException extends RuntimeException{
    public FailedToSendEmailException(String message) {
        super(message);
    }
}
