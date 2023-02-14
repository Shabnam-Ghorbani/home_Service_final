package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.data.model.entity.Address;
import ir.maktab.home_service.data.model.repository.AddressRepository;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.service.interf.AddInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService implements AddInter {
    private final AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        Optional<Address> foundedAddress = addressRepository.findByZipCode(address.getZipCode());
        return foundedAddress.orElseGet(() -> addressRepository.save(address));
    }

    @Override
    public Address findByZipCode(Long zipCode) {
        Optional<Address> address = addressRepository.findByZipCode(zipCode);
        return address.orElseThrow(() -> new EntityNotExistException("address not exist!"));
    }
}
