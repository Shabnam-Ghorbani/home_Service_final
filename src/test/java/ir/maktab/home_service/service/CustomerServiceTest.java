package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.enamiration.Role;
import ir.maktab.home_service.data.model.enamiration.UserStatus;
import ir.maktab.home_service.data.model.entity.Customer;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.exception.InCorrectException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceTest {
    Customer customer = Customer.builder()
            .firstname("saba")
            .lastname("abdi")
            .emailAddress("sabaabdi64@gmail.com")
            .password("Saba6040")
            .role(Role.CUSTOMER)
            .userStatus(UserStatus.NEW)
            .credit(0L)
            .build();
    @Autowired
    private CustomerService customerService;

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
        Customer customer = Customer.builder()
                .firstname("saba")
                .lastname("abdi")
                .emailAddress("sabaabdi64@gmail.com")
                .password("Saba6040")
                .role(Role.CUSTOMER)
                .userStatus(UserStatus.NEW)
                .credit(0L)
                .build();
        boolean thrown = assertThrows(EntityIsExistException.class, () -> customerService.save(customer)).
                getMessage().contains("this EmailAddress exist");
    }

    @Test
    @Order(3)
    public void ExistCustomerEmail_Test() {
        Customer customer = customerService.findByEmailAddress("sabaabdi64@gmail.com");
        assertNotNull(customer);
    }

    @Test
    @Order(4)
    public void notExistCustomerEmail_Test() {
        EntityNotExistException thrown =
                assertThrows(EntityNotExistException.class, () -> customerService.findByEmailAddress("shabnamgh@gmail.com"));
        assertTrue(thrown.getMessage().contains("emailAddress not exist!"));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    public void changePassword_Test() {
        Customer customer1 = customerService.findByEmailAddress("sabaabdi64@gmail.com");
        Customer updatedUser = customerService.changePassword(customer1, "Saba6040", "shAb1378");
        assertEquals(updatedUser.getPassword(), "shAb1378");
    }

    @Test
    @Order(6)
    public void wrongCurrentPass_Test() {
        Customer customer1 = customerService.findByEmailAddress("sabaabdi64@gmail.com");
        InCorrectException thrown =
                assertThrows(InCorrectException.class, () -> customerService.changePassword(customer1, "ShabNam1234", "sHsH1378"));
        assertTrue(thrown.getMessage().contains("password is wrong!"));
    }
}
