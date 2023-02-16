package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.entity.BaseService;
import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.SubService;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.service.impl.BaseServiceService;
import ir.maktab.home_service.service.impl.ExpertService;
import ir.maktab.home_service.service.impl.SubServiceService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubServiceTest {
    @Autowired
    SubServiceService subServiceService;
    @Autowired
    BaseServiceService baseServiceService;
    @Autowired
    ExpertService expertService;
    SubService subService;

    @Test
    @Order(1)
    public void subServiceWhitExistBaseService_Test() {
        BaseService existBaseService = baseServiceService.findByName("Cleaning and hygiene");
        subService = SubService.builder()
                .name("cleaning")
                .basePrice(100000L)
                .description("description")
                .baseService(existBaseService)
                .build();
        SubService savedSubService = subServiceService.save(subService);
        assertEquals(subService, savedSubService);
    }

    @Test
    @Order(2)
    public void existSubServiceWhitExistBaseService_Test() {
        BaseService existBaseService = baseServiceService.findByName("Cleaning and hygiene");
        subService = SubService.builder()
                .name("cleaning")
                .basePrice(100000L)
                .description("description")
                .baseService(existBaseService)
                .build();
        EntityIsExistException thrown = assertThrows(EntityIsExistException.class, () -> subServiceService.save(subService));
        assertTrue(thrown.getMessage().contains("this subService exist!"));
    }

    @Test
    @Order(3)
    public void subServiceWhitNotExistBaseService_Test() {
        BaseService notExistBaseService = BaseService.builder().name("Darmani").build();
        subService = SubService.builder()
                .name("cleaning")
                .basePrice(100000L)
                .description("description")
                .baseService(notExistBaseService)
                .build();
        boolean thrown = assertThrows(EntityNotExistException.class, () -> subServiceService.save(subService)).
                getMessage().contains("this baseService not exist");
    }

    @Test
    @Order(4)
    public void subServiceName_FindByName_Test() {
        SubService subService = subServiceService.findByName("cleaning");
        assertNotNull(subService);
    }


    @Test
    @Order(5)
    public void SubService_Update_Test() {
        SubService existSubService = subServiceService.findByName("cleaning");
        existSubService.setDescription("new description");
        SubService updatedSubService = subServiceService.update(existSubService);
        assertEquals(existSubService, updatedSubService);
    }

//    @Test
//    @Order(7)
//    public void subServiceAndExpert_AddExpertToSubService_Test() {
//        SubService subService = subServiceService.findByName("cleaning");
//        Expert expert = expertService.findById(1);
//        SubService updatedSubService = subServiceService.addExpertToSubService(expert, subService);
//        Set<Expert> experts = updatedSubService.getExperts();
//        assertTrue(experts.contains(expert));
//    }

//    @Test
//    @Order(6)
//    public void subServiceAndExpert_RemoveExpertFromSubService_Test() {
//        SubService subService = subServiceService.findById(1);
//        Expert expert = expertService.findById(1);
//        SubService updatedSubService = subServiceService.removeExpertFromSubService(expert, subService);
//        Set<Expert> experts = updatedSubService.getExperts();
//        assertFalse(experts.contains(expert));
//    }
}
