package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.SubService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubServiceRepository extends CrudRepository<SubService, Integer> {
    Optional<SubService> findByName(String name);}
