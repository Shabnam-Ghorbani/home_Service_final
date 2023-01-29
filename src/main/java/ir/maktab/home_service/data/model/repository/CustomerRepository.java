package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmailAddress(String email);
}
