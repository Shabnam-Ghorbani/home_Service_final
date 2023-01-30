package ir.maktab.home_service.service;

import ir.maktab.home_service.data.model.enamiration.OfferStatus;
import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.Customer;
import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.data.model.repository.CustomerRepository;
import ir.maktab.home_service.exception.EntityIsExistException;
import ir.maktab.home_service.exception.EntityNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final OrderService orderService;
    private final OfferService offerService;

    public Customer save(Customer customer) {
        Optional<Customer> foundedCustomer = customerRepository.findByEmailAddress(customer.getEmailAddress());
        if (foundedCustomer.isPresent()) {
            throw new EntityIsExistException("this emailAddress exist!");
        } else {
            return customerRepository.save(customer);
        }
    }

    public Customer findByEmailAddress(String emailAddress) {
        Optional<Customer> customer = customerRepository.findByEmailAddress(emailAddress);
        return customer.orElseThrow(() -> new EntityNotExistException("emailAddress not exist!"));
    }

    public void acceptOfferForOrder(Order order, Expert expert) {
        order.setExpert(expert);
        order.setOrderStatus(OrderStatus.WAITING_FOR_THE_EXPERT_TO_ARRIVE);
        orderService.save(order);
        Offer acceptedOffer = offerService.findByOrderAndExpert(order, expert);
        Set<Offer> offers = order.getOffers();
        for (Offer offer : offers) {
            if (offer.equals(acceptedOffer)) {
                offer.setOfferStatus(OfferStatus.ACCEPTED);
            } else {
                offer.setOfferStatus(OfferStatus.REJECTED);
            }
            offerService.save(offer);
        }
    }
}
