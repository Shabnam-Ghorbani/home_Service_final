package ir.maktab.home_service.dto.offerdto;

import ir.maktab.home_service.data.model.enamiration.OfferStatus;
import ir.maktab.home_service.data.model.enamiration.TimeType;
import ir.maktab.home_service.dto.expertdto.ExpertDto;
import ir.maktab.home_service.dto.orderdto.OrderDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {

    private Long identificationNumber;

    private ExpertDto expertDto;

    private OrderDto orderDto;

    private Date registrationDate;

    private Long proposedPrice;

    private int durationOfWork;

    private Date startTime;

    private OfferStatus offerStatus;



    public OfferDto(Long proposedPrice, TimeType timeType, Integer durationTime, Double score, LocalDateTime endTime) {
    }
}
