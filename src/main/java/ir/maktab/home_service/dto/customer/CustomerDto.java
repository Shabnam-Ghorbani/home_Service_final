package ir.maktab.home_service.dto.customer;

import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.enamiration.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long identificationNumber;

    private String firstname;

    private String lastname;

    private String emailAddress;

    private PersonStatus personStatus;

    private Date registrationDate;

    private Long credit;

    private Role role;
}
