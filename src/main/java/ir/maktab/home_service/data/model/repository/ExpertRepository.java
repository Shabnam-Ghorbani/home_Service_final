package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.Expert;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends CrudRepository<Expert, Integer> {
    Optional<Expert> findByEmailAddress(String email);}
