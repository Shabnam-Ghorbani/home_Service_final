package ir.maktab.home_service.dto.persondto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {
    private String firstname;

    private String lastname;

    @Email(message = "the format of the email is incorrect!")
    private String  emailAddress;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8}$",
            message = "the password must contain numbers, lowercase and uppercase letters")
    private String password;
}
