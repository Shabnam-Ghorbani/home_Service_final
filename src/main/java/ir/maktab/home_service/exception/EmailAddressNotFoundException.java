package ir.maktab.home_service.exception;

public class EmailAddressNotFoundException extends RuntimeException{
    public EmailAddressNotFoundException(String message) {
        super(message);
    }
}
