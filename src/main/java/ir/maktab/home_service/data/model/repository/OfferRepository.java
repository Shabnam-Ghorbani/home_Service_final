package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findByOrder(Order order, Sort firstSort);

    Optional<Offer> findById(Integer id);


    Optional<Offer> findByOrderAndExpert(Order order, Expert expert);

    Offer save(Offer offer);

    @Query("select o from Offer o where o.order.id = :orderId order by o.proposedPrice")
    List<Offer> findOrderBasedPrice(Integer orderId);

    @Query("select o from Offer o where o.order.id = :orderId order by o.expert.score desc")
    List<Offer> viewOrderOffersBaseOnExpertScore(Integer orderId);

    @Query("select o from Offer o where o.order.id = :id  order by o.expert.score desc")
    List<Offer> findOfferOrderExpertScore(Integer id);

    @Modifying
    @Query("update Offer o set o.isAccept = :isAccept where o.id = :offerId")
    int editOffer(Integer offerId, Boolean isAccept);

    List<Offer> findOfferByExpertIdAndIsAccept(Integer expertId, boolean isAccept);

    @Query("select count(o.expert.id) from Offer o where o.expert.id = :expertId ")
    int NumberOfOffers(Integer expertId);

    @Query(" select count(o.expert.id) from Offer o where o.expert.id = :expertId and o.isAccept = :isAccept ")
    int NumberOfOffers(Integer expertId, boolean isAccept);

    @Query(" select count(o.expert.id) from Offer o where o.expert.id = :expertId")
    int calculateNumberOfRegisteredOffers(Integer expertId);

    @Query("select count(o.expert.id) from Offer o where o.expert.id = :expertId and o.isAccept = :isAccept ")
    int calculateNumberOfRegisteredOffers(Integer expertId, boolean isAccept);

}
