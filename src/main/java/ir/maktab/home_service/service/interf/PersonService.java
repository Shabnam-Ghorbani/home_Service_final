package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.data.model.entity.Person;

import java.util.Optional;

public interface PersonService {
    Optional<Person> findByEmailAddress(String emailAddress);

}
