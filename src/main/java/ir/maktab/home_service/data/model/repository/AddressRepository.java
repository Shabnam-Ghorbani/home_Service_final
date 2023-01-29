package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address, Integer> {
    Optional<Address> findByZipCode(Long zipCode);
}
