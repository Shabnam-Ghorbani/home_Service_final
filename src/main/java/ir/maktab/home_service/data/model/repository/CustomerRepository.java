package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
    Optional<Customer> findByEmailAddress(String email);

    @Modifying
    @Query("update Customer c set c.credit = :newCredit where c.id = :id")
    int updateCredit(Integer id, Long newCredit);

    boolean existsByEmail(String emailAddress);

    @Modifying
    @Query(" update Customer c set c.password = :newPassword where c.id = :id")
    int editPassword(Integer id, String newPassword);


}
