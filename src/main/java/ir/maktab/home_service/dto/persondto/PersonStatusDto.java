package ir.maktab.home_service.dto.persondto;

import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonStatusDto {
    private Integer id;

    private PersonStatus personStatus;
}
