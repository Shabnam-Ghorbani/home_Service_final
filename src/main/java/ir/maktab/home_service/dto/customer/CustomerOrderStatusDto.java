package ir.maktab.home_service.dto.customer;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerOrderStatusDto {

    private Integer id;

    private OrderStatus orderStatus;
}
