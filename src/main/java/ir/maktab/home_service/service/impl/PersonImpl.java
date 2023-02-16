package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.data.model.entity.Person;
import ir.maktab.home_service.data.model.repository.PersonRepository;
import ir.maktab.home_service.service.interf.PersonService;

import java.util.Optional;

public class PersonImpl implements PersonService {
    PersonRepository personRepository;

    @Override
    public Optional<Person> findByEmailAddress(String emailAddress) {
        try {
            return personRepository.findByEmailAddress(emailAddress);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

