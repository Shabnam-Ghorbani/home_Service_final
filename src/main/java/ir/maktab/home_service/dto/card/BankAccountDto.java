package ir.maktab.home_service.dto.card;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDto {

    @Pattern(regexp = "(?<!\\d)\\d{16}(?!\\d)|(?<!\\d[ _-])(?<!\\d)\\d{4}(?=([_ -]))(?:\\1\\d{4}){3}(?![_ -]?\\d)",
            message = "The card number is invalid!")
    private String cardNumber;

    @Pattern(regexp = "^\\d{4}$", message = "year is invalid!")
    private String year;

    @Pattern(regexp = "^\\d{2}$", message = "month is invalid!")
    private String month;

    @Pattern(regexp = "^[0-9]{3,4}$", message = " cvv2 is invalid!")
    private String cvv2;


}
