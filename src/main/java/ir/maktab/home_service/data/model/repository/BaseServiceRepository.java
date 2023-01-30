package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.BaseService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseServiceRepository extends CrudRepository<BaseService, Integer> {
    Optional<BaseService> findByName(String name);
}
