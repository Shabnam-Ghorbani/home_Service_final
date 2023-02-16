package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.Address;
import ir.maktab.home_service.data.model.entity.Customer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.data.model.entity.SubService;
import ir.maktab.home_service.service.impl.AddressService;
import ir.maktab.home_service.service.impl.CustomerService;
import ir.maktab.home_service.service.impl.OrderService;
import ir.maktab.home_service.service.impl.SubServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceTest {
    @Autowired
    CustomerService customerService;
    @Autowired
    SubServiceService subServiceService;
    @Autowired
    OrderService orderService;
    @Autowired
    AddressService addressService;
    Order order;


    @Test
    @org.junit.jupiter.api.Order(1)
    public void saveOrder_Test() {

        Customer customer = customerService.findByEmailAddress("sabaabdi64@gmail.com");
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse("1400-10-15 12:30");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SubService subService = subServiceService.findById(3);
        Address address = addressService.findByZipCode(89350382L);
        order = Order.builder()
                .proposedPrice(1000000L)
                .jobDescription("description")
                .dateOfWorkPerformed(date)
                .address(address)
                .customer(customer)
                .orderStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTIONS)
                .subService(subService)
                .build();

        Order savedOrder = orderService.save(order);
        assertEquals(order, savedOrder);
    }
}
