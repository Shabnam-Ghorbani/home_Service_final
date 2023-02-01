package ir.maktab.home_service.exception;

public class EntityIsExistException extends RuntimeException {
    public EntityIsExistException(String message) {
        super(message);
    }
}
