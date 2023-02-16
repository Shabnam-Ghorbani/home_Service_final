package ir.maktab.home_service.dto.servicedto;

import ir.maktab.home_service.dto.addres.AddressDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubServiceOrderDTO {

    private Integer id;

    private Long proposedPrice;

    private String description;

    private String executionDate;

    private String executionTime;

    private AddressDto addressDto;
}
