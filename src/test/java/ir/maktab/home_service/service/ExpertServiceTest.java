package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.enamiration.Role;
import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.exception.InCorrectException;
import ir.maktab.home_service.service.impl.ExpertService;
import ir.maktab.home_service.service.impl.Image;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpertServiceTest {
    public Expert expert;
    @Autowired
    ExpertService expertService;
    @Autowired
    Image imageReader;

    @Test
    @Order(1)
    public void addExpert_Test() {
        byte[] image = new byte[0];
        try {
            image = imageReader.fileToBytes("image/shabnam.jpg.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        expert = Expert.builder()
                .firstname("shabnam")
                .lastname("ghorbani")
                .emailAddress("shabnamghorbani90@gmail.com")
                .password("ShabNam1234")
                .credit(0L)
                .personStatus(PersonStatus.NEW)
                .score(5.0)
                .photo(image)
                .role(Role.EXPERT)
                .build();
        Expert savedExpert = expertService.save(expert);
        assertEquals(expert, savedExpert);
    }

    @Test
    public void givenDuplicateExpert_WhenSave_ThenThrowException() {
        EntityIsExistException thrown = assertThrows(EntityIsExistException.class, () -> expertService.save(expert));
        assertTrue(thrown.getMessage().contains("this emailAddress exist!"));
    }

    @Test
    public void ExistCustomerEmail_ُTest() {
        Expert expert = expertService.findByEmailAddress("shabnamghorbani90@gmail.com");
        assertNotNull(expert);
    }

    @Test
    public void notExistCustomerEmail_Test() {
        EntityNotExistException thrown =
                assertThrows(EntityNotExistException.class, () -> expertService.findByEmailAddress("arman2370@gmail.com"));
        assertTrue(thrown.getMessage().contains("emailAddress not exist!"));
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    public void changePassword_Test() {
        Expert expert1 = expertService.findById(352); //My userName
        Expert updatedUser = expertService.changePassword(expert1, "ShabNam1234", "shAb9090");
        assertEquals(updatedUser.getPassword(), "shAb9090");
    }

    @Test
    @Order(6)
    public void wrongCurrentPass_Test() {
        Expert expert1 = expertService.findByEmailAddress("shabnamghorbani90@gmail.com");
        InCorrectException thrown =
                assertThrows(InCorrectException.class, () -> expertService.changePassword(expert1, "Saba6040", "chaPoo1378"));
        assertTrue(thrown.getMessage().contains("password is wrong!"));
    }
}
