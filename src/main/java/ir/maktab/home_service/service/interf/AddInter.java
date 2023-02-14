package ir.maktab.home_service.service.interf;


import ir.maktab.home_service.data.model.entity.Address;

public interface AddInter {

    Address save(Address address);

    Address findByZipCode(Long zipCode);
}