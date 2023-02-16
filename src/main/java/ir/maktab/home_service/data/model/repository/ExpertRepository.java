package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.entity.Expert;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends CrudRepository<Expert, Integer> {
    Optional<Expert> findByEmailAddress(String email);

    Optional<Expert> findById(Integer id);

    @Modifying
    @Query("update Expert e set e.score = :newScore where e.id = :expertId")
    Integer updateScore(Integer expertId, Integer newScore);

    @Modifying
    @Query("update Expert e set e.personStatus = :personStatus where e.emailAddress = :emailAddress")
    int changePersonStatus(String emailAddress, PersonStatus personStatus);

    @Modifying
    @Query("update Expert e set e.isActive = :isActive where e.id = :id")
    int changeExpertActivation(Integer id, Boolean isActive);

    @Modifying
    @Query("update Expert e set e.credit = :newCredit where e.id = :expertId ")
    int updateCredit(Integer expertId, Long newCredit);
}
