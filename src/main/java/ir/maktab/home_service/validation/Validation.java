package ir.maktab.home_service.validation;

import ir.maktab.home_service.exception.InvalidEmailException;
import ir.maktab.home_service.exception.InvalidNameException;
import ir.maktab.home_service.exception.InvalidNumberException;
import ir.maktab.home_service.exception.InvalidPasswordException;
import org.springframework.stereotype.Component;

@Component
public class Validation {
    public boolean validateEmail(String email) throws InvalidEmailException {
        if (email.matches("^((\"[\\w-\\s]+\")|([\\w-]+(?:\\.[\\w-]+)*)|(\"[\\w-\\s]+\")([\\w-]+(?:\\.[\\w-]+)*))(@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$)|(@\\[?((25[0-5]\\.|2[0-4][0-9]\\.|1[0-9]{2}\\.|[0-9]{1,2}\\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\\]?$)")) {
            return true;
        }
        throw new InvalidEmailException("invalid Email!");
    }

    public boolean validateName(String name) throws InvalidNameException {
        if (name.length() > 2 && name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$") && name.length() < 20) {
            return true;
        }
        throw new InvalidNameException("invalid name!");
    }

    public boolean validateNumber(String number) throws InvalidNumberException {
        if (number.matches("^([+]?[1-9]\\d*|0)$")) {
            return true;
        }
        throw new InvalidNumberException("invalid number!");
    }

    public boolean validatePassword(String password) throws InvalidPasswordException {
        if (password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")) {
            return true;
        }
        throw new InvalidPasswordException("invalid password!");
    }
}
