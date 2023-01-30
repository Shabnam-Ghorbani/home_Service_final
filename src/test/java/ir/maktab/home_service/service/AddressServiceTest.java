package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AddressServiceTest {
    @Autowired
    private AddressService addressService;
    private static Address address;

    @Test
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
