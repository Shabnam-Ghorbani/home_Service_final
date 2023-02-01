package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.data.model.entity.SubService;
import ir.maktab.home_service.data.model.repository.OfferRepository;
import ir.maktab.home_service.data.model.repository.OrderRepository;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.exception.NotMatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final OfferRepository offerRepository;
    private final OrderRepository orderRepository;

    public Offer save(Offer offer) {
        return offerRepository.save(offer);
    }

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

    public List<Offer> findByOrder(Order order) {
        return offerRepository.findByOrder((jakarta.persistence.criteria.Order) order, Sort.by("expert.score", "proposedPrice").descending());
    }

    public Offer findByOrderAndExpert(Order order, Expert expert) {
        Optional<Offer> offer = offerRepository.findByOrderAndExpert((jakarta.persistence.criteria.Order) order, expert);
        return offer.orElseThrow(() -> new EntityNotExistException("offer not found!"));
    }
}
