package ir.maktab.home_service.dto.expertdto;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpertOrderHistoryDTO {

    private Integer id;
    private Boolean isAccept;
    private OrderStatus orderStatus;
}
