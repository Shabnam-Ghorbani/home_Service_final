package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.data.model.entity.SubService;
import ir.maktab.home_service.data.model.repository.OfferRepository;
import ir.maktab.home_service.data.model.repository.OrderRepository;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.exception.NotMatchException;
import ir.maktab.home_service.service.interf.OffeInter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OfferService implements OffeInter {
    private final OfferRepository offerRepository;
    private final OrderRepository orderRepository;

    @Override
    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

    @Override
    public Offer findById(Integer id) {
        Optional<Offer> offer = offerRepository.findById(id);
        return offer.orElseThrow(() -> new EntityNotExistException("offer not exist!"));
    }

    //ToDo یافتن بر اساس قیمت پیشنهادی
    @Transactional
    @Override
    public List<Offer> findOrderBasedPrice(Integer orderId) {
        try {
            return offerRepository.findOrderBasedPrice(orderId);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    //ToDo یافتن پیشنهاد بر اساس امتیاز متخصص
    @Override
    public List<Offer> findOrderBasedExpertScore(Integer orderId) {
        try {
            return offerRepository.viewOrderOffersBaseOnExpertScore(orderId);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

   @Transactional
    @Override
    public int editOffer(Integer offerId, Boolean isAccept) {
        return offerRepository.editOffer(offerId, isAccept);
    }

    @Override
    public Order addOfferToOrder(Offer offer) {
        Set<SubService> expertServices = offer.getExpert().getServices();
        SubService subService = offer.getOrder().getSubService();
        if (expertServices.contains(subService) && subService.getBasePrice() <= offer.getProposedPrice()) {
            Offer savedOffer = save(offer);
            System.out.println(savedOffer);
            Order order = savedOffer.getOrder();
            order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SELECTION);
            order.getOffers().add(offer);
            orderRepository.save(order);
            return order;
        } else {
            throw new NotMatchException("your offer is not match for this Order!");
        }
    }

    @Override
    public List<Offer> findOfferOrderExpertScore(Integer id) {
        List<Offer> offers = offerRepository.findOfferOrderExpertScore(id);
        if (offers.isEmpty())
            throw new EntityNotExistException(" offers not exist!");
        return offers;
    }

    //ToDo sort  with score this method and method bala :) ShaBoo ba in SaThe ZaBan FaGhat AfghanEstAn GarDan MiGirAdEt
    @Override
    public List<Offer> findByOrder(Order order) {
        return offerRepository.findByOrder((jakarta.persistence.criteria.Order) order,
                Sort.by("expert.score", "proposedPrice").descending());
    }

    @Override
    public Offer findByOrderAndExpert(Order order, Expert expert) {
        Optional<Offer> offer = offerRepository.findByOrderAndExpert((jakarta.persistence.criteria.Order) order, expert);
        return offer.orElseThrow(() -> new EntityNotExistException("offer not found!"));
    }

    @Transactional
    @Override
    public List<Offer> findOfferByExpertAndAccept(Integer expertId, boolean isAccept) {
        try {
            return offerRepository.findOfferByExpertIdAndIsAccept(expertId, isAccept);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public int NumberOfOffers(Integer expertId) {
        return offerRepository.NumberOfOffers(expertId);
    }

    @Override
    public int NumberOfOffers(Integer expertId, Boolean isAccept) {
        return offerRepository.NumberOfOffers(expertId, isAccept);
    }

    @Override
    public int calculateNumberOfRegisteredOffers(Integer expertId) {
        return offerRepository.calculateNumberOfRegisteredOffers(expertId);
    }

    @Override
    public int calculateNumberOfRegisteredOffers(Integer expertId, Boolean isAccept) {
        return offerRepository.calculateNumberOfRegisteredOffers(expertId, isAccept);
    }
}
