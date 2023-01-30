package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.enamiration.Role;
import ir.maktab.home_service.data.model.enamiration.UserStatus;
import ir.maktab.home_service.data.model.entity.Customer;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CustomerServiceTest {
    @Autowired
    private CustomerService customerService;
    Customer customer = Customer.builder()
            .firstname("saba")
            .lastname("abdi")
            .emailAddress("sabaabdi64@gmail.com")
            .password("Saba6040")
            .role(Role.CUSTOMER)
            .userStatus(UserStatus.NEW)
            .credit(0L)
            .build();

    @Test
    @Order(1)
    public void Customer_Save_Test() {
        Customer customer = Customer.builder()
                .firstname("saba")
                .lastname("abdi")
                .emailAddress("sabaabdi64@gmail.com")
                .password("Saba6040")
                .role(Role.CUSTOMER)
                .userStatus(UserStatus.NEW)
                .credit(0L)
                .build();
        Customer savedCustomer = customerService.save(customer);
        assertEquals(customer, savedCustomer);
    }

    @Test
    @Order(2)
    public void givenDuplicateCustomer_WhenSave_ThenThrowException() {
        EntityIsExistException thrown = assertThrows(EntityIsExistException.class, () -> customerService.save(customer));
        assertTrue(thrown.getMessage().contains("this emailAddress exist!"));
    }

    @Test
    @Order(3)
    public void ExistCustomerEmail_Test() {
        Customer customer = customerService.findByEmailAddress("sabaabdi64@gmail.com");
        assertNotNull(customer);
    }

    @Test
    @Order(4)
    public void givenNotExistCustomerEmail_WhenFindByEmailAddress_ThenThrowException() {
        EntityNotExistException thrown =
                assertThrows(EntityNotExistException.class, () -> customerService.findByEmailAddress("shabnamgh@gmail.com"));
        assertTrue(thrown.getMessage().contains("emailAddress not exist!"));
    }
}
