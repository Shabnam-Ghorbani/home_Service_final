package ir.maktab.home_service.dto.filter;

import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExpertFilterDTO {

    private String firstname;

    private String lastname;

    private String emailAddress;

    private Boolean isActive;

    private PersonStatus personStatus;

    private Integer maxScore;

    private Integer minScore;

    private Long maxCredit;

    private Long minCredit;

    private String maxCreationDate;

    private String minCreationDate;
}
