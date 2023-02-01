package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.entity.BaseService;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BaseServiceTest {
    BaseService baseService = BaseService.builder().name("Cleaning and hygiene").build();
    @Autowired
    private BaseServiceService baseServiceService;

    @Test
    @Order(1)
    public void baseService_SaveTest() {
        BaseService baseService = BaseService.builder().name("Cleaning and hygiene").build();
        BaseService savedBaseService = baseServiceService.save(baseService);
        assertEquals(baseService, savedBaseService);
    }

    @Test
    @Order(2)
    public void duplicateBaseService_Test() {
        baseService = BaseService.builder().name("Cleaning and hygiene").build();
        boolean thrown = assertThrows(EntityIsExistException.class, () -> baseServiceService.save(baseService)).
                getMessage().contains("this BaseService exist");
    }

    @Test
    @Order(3)
    public void existBaseServiceName_Test() {
        BaseService baseService1 = baseServiceService.findByName("Cleaning and hygiene");
        assertNotNull(baseService1);
    }

    @Test
    @Order(4)
    public void NotExistBaseServiceName_Test() {
        EntityNotExistException thrown = assertThrows(EntityNotExistException.class, () -> baseServiceService.findByName("mekanik"));
        assertTrue(thrown.getMessage().contains("this baseService not exist!"));
    }
}
