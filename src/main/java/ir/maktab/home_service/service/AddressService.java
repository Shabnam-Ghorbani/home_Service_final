package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.entity.Address;
import ir.maktab.home_service.data.model.repository.AddressRepository;
import ir.maktab.home_service.exception.EntityNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;

    public Address save(Address address) {
        Optional<Address> foundedAddress = addressRepository.findByZipCode(address.getZipCode());
        return foundedAddress.orElseGet(() -> addressRepository.save(address));
    }

    public Address findByZipCode(Long zipCode) {
        Optional<Address> address = addressRepository.findByZipCode(zipCode);
        return address.orElseThrow(() -> new EntityNotExistException("address not exist!"));
    }
}
