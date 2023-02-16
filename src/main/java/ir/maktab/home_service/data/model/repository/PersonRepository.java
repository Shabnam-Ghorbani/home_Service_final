package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByEmailAddress(String emailAddress);

}
