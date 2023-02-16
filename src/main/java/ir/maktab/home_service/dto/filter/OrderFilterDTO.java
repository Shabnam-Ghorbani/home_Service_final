package ir.maktab.home_service.dto.filter;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.dto.addres.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderFilterDTO {

    private AddressDto addressDto;

    private String description;

    private OrderStatus orderStatus;

    private Integer id;

    private String subServiceName;

    private String BaseServiceName;

    private Long maxProposedPrice;

    private Long proposedPrice;

    private Long minProposedPrice;

    private String maxCreationDate;

    private LocalDateTime CreationDate;

    private String minCreationDate;

    private String maxUpdateTime;

    private LocalDateTime updateTime;

    private String minUpdateTime;
}
