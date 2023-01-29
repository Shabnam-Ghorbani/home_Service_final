package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    Optional<Manager> findByUsername(String username);
}
