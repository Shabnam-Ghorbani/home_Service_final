package ir.maktab.home_service.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFilterDto {

    private String firstname;

    private String lastname;

    private String email;

    private Boolean isActive;

    private Long maxCredit;

    private Long credit;

    private Long minCredit;

    private String maxCreationDate;

    private LocalDateTime CreationDate;

    private String minCreationDate;
}
