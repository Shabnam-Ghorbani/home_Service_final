package ir.maktab.home_service.dto.expertdto;

import ir.maktab.home_service.data.model.enamiration.TimeType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderExpertOfferDTO {

    private Integer id;

    private String offer;

    private TimeType timeType;

    private Long proposedPrice;

    private Integer durationTime;

    private String endDate;

    private String endTime;
}
