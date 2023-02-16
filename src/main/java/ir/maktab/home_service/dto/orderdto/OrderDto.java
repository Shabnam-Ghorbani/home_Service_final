package ir.maktab.home_service.dto.orderdto;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;

import ir.maktab.home_service.dto.addres.AddressDto;
import ir.maktab.home_service.dto.customer.CustomerDto;
import ir.maktab.home_service.dto.expertdto.ExpertDto;
import ir.maktab.home_service.dto.servicedto.SubServiceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long identificationNumber;

    private SubServiceDto subServiceDto;

    private Long proposedPrice;

    private String jobDescription;

    private Date orderRegistrationDate;

    private Date dateOfWorkPerformed;

    private AddressDto addressDto;

    private CustomerDto customerDto;

    private OrderStatus orderStatus;

    private ExpertDto expertDto;
}
