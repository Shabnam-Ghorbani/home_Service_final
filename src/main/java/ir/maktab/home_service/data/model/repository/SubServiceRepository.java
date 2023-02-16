package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.SubService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubServiceRepository extends CrudRepository<SubService, Integer> {
    Optional<SubService> findByName(String name);

    Optional<SubService> findById(Integer id);

    int deleteSubServiceById(Integer id);


    @Modifying
    @Query("update SubService ss set ss.basePrice = :basePrice, ss.description = :description where ss.id = :id")
    int editBasePriceAndDescription(Integer id, Long basePrice, String description);
}
