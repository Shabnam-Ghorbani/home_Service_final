package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.entity.Address;
import ir.maktab.home_service.service.impl.AddressService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddressServiceTest {
    private static Address address;
    @Autowired
    private AddressService addressService;

    @Test
    @Order(1)
    public void saveAddressTest() {
        address = Address
                .builder()
                .city("tehran")
                .state("satarkhan")
                .streetAddress("kashanipour")
                .houseNumber("25")
                .zipCode(89350382L)
                .build();
        Address saved = addressService.save(address);
        assertEquals(address, saved);
    }

    @Test
    @Order(2)
    public void ExistAddressTest() {
        address = Address
                .builder()
                .city("tehran")
                .state("satarkhan")
                .streetAddress("kashanipour")
                .houseNumber("25")
                .zipCode(89350382L)
                .build();
        Address savedAddress = addressService.save(address);
        assertEquals(address.getZipCode(), savedAddress.getZipCode());
    }
}
