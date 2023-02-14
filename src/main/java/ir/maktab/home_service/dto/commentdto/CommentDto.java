package ir.maktab.home_service.dto.commentdto;

import ir.maktab.home_service.dto.orderdto.OrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long identificationNumber;

    private Double score;

    private String opinion;

    private OrderDto orderDto;
}
