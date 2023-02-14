package ir.maktab.home_service.dto.servicedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseServiceSubServiceDto {
    private Integer baseServiceId;

    private String name;

    private Long basePrice;

    private String description;
}
