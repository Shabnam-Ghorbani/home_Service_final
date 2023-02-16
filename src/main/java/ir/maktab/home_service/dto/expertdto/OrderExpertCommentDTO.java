package ir.maktab.home_service.dto.expertdto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderExpertCommentDTO {

    private Integer orderId;

    private Integer expertId;

    private Integer score;

    private String comment;
}
