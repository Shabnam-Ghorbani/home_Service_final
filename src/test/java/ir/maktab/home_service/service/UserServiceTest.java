package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.enamiration.Role;
import ir.maktab.home_service.data.model.enamiration.UserStatus;
import ir.maktab.home_service.data.model.entity.Customer;
import ir.maktab.home_service.data.model.entity.User;
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
public class UserServiceTest {
    @Autowired
    UserService userService;
    User user = Customer.builder()
            .firstname("saba")
            .lastname("abdi")
            .emailAddress("sabaabdi64@gmail.com")
            .password("Saba6040")
            .role(Role.CUSTOMER)
            .userStatus(UserStatus.NEW)
            .credit(0L)
            .build();

    @Test
    @org.junit.jupiter.api.Order(1)
    public void addUser_Test() {
        User savedUser = userService.save(user);
        assertEquals(user, savedUser);
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void DuplicateUser_Test() {
        EntityIsExistException thrown = assertThrows(EntityIsExistException.class, () -> userService.save(user));
        assertTrue(thrown.getMessage().contains("this emailAddress exist!"));
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void givenExistUserEmail_WhenFindByEmailAddress_ThenReturnUser() {
        User user = userService.findByEmailAddress("sabaabdi64@gmail.com");
        assertNotNull(user);
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void givenNotExistUserEmail_WhenFindByEmailAddress_ThenThrowException() {
        EntityNotExistException thrown =
                assertThrows(EntityNotExistException.class, () -> userService.findByEmailAddress("arman1380@gmail.com"));
        assertTrue(thrown.getMessage().contains("emailAddress not exist!"));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    public void givenUserAndCurrentPass_WhenChangePassword_returnUpdatedUser() {
        User user = userService.findByEmailAddress("shabnamghorbani90@gmail.com");
        User updatedUser = userService.changePassword(user, "ShabNam1234", "shAb1378");
        assertEquals(updatedUser.getPassword(), "shAb1378");
    }

    @Test
    @Order(6)
    public void givenUserAndWrongCurrentPass_WhenChangePassword_ThenThrowException() {
        User user = userService.findByEmailAddress("shabnamghorbani90@gmail.com");
        InCorrectException thrown =
                assertThrows(InCorrectException.class, () -> userService.changePassword(user, "shAb1378", "chaPoo1378"));
        assertTrue(thrown.getMessage().contains("password is wrong!"));
    }
}
