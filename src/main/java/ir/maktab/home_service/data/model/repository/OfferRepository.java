package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends PagingAndSortingRepository<Offer, Integer> {
    List<Offer> findByOrder(Order order, Sort firstSort);

    Optional<Offer> findByOrderAndExpert(Order order, Expert expert);

    Offer save(Offer offer);
}
