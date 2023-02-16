package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.data.model.entity.Order;

import java.util.List;

public interface OffeInter {

    Offer save(Offer offer);

    Offer findById(Integer id);

    List<Offer> findOrderBasedPrice(Integer orderId);

    List<Offer> findOrderBasedExpertScore(Integer orderId);

    int editOffer(Integer offerId, Boolean isAccept);

    Order addOfferToOrder(Offer offer);

    List<Offer> findOfferOrderExpertScore(Integer id);

    List<Offer> findByOrder(Order order);

    Offer findByOrderAndExpert(Order order, Expert expert);

    List<Offer> findOfferByExpertAndAccept(Integer expertId, boolean isAccept);

    int calculateNumberOfRegisteredOffers(Integer expertId);

    int calculateNumberOfRegisteredOffers(Integer expertId, Boolean isAccept);

    int NumberOfOffers(Integer expertId);

    int NumberOfOffers(Integer expertId, Boolean isAccept);
}
