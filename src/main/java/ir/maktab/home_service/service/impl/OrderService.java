package ir.maktab.home_service.service.impl;


import ir.maktab.home_service.data.model.enamiration.OfferStatus;
import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.data.model.repository.OrderRepository;
import ir.maktab.home_service.dto.filter.OrderFilterDTO;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.service.interf.OrdInter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService implements OrdInter {
    private final OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByCustomer(Integer id) {
        try {
            return orderRepository.findByCustomer(id);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public Order findById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new EntityNotExistException("this order not exist!"));
    }

    @Override
    //ToDo یافتن سفارش بر اساس آیدی  زیرخدمات  و وضعیت سفارش
    public List<Order> findBySubServiceIdAndOrderStatus(Integer id, OrderStatus orderStatusOne, OrderStatus orderStatusTwo) {
        try {
            return orderRepository.findBySubServiceIdAndOrderStatus(id, orderStatusOne, orderStatusTwo);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    //ToDo یافتن سفارش بر اساس آیدی مشتری و وضعیت سفارش
    public List<Order> findByCustomerIdAndOrderStatus(Integer customerId, OrderStatus orderStatus) {
        try {
            return orderRepository.findByCustomerIdAndOrderStatus(customerId, orderStatus);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Transactional
    @Override
    //ToDo تغییر وضعیت سفارش
    public int changeOrderStatus(Integer id, OrderStatus orderStatus, OrderStatus newOrderStatus) {
        return orderRepository.changeOrderStatus(id, orderStatus, newOrderStatus);
    }

    @Override
    //ToDo یافتن سفارش بر اساس آیدی  زیرخدمات  و وضعیت سفارش
    public List<Order> findSubServiceIdAndOrderStatus(Integer id, OrderStatus orderStatusOne, OrderStatus orderStatusTwo) {
        try {
            return orderRepository.findSubServiceIdAndOrderStatus(id, orderStatusOne, orderStatusTwo);
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    //todo تعداد سفارشات ثبت شده
    public int NumberOfRegisteredOrders(Integer id) {
        return orderRepository.NumberOfRegisteredOrders(id);
    }

    @Override
    //todo تعداد سفارشات ثبت شده بر اساس آیدی و وضعیت سفارش
    public int NumberOfRegisteredOrders(Integer id, OrderStatus orderStatus) {
        return orderRepository.NumberOfRegisteredOrders(id, orderStatus);
    }

    @Override
    //todo یافتن سفارش پذیرفته شده
    public Offer findAcceptedOfferOfOrder(Order order) {
        Offer acceptedOffer = null;
        if (order.getOrderStatus().equals(OrderStatus.PAID)) {
            Set<Offer> offers = order.getOffers();
            for (Offer offer : offers) {
                if (offer.getOfferStatus().equals(OfferStatus.ACCEPTED)) {
                    acceptedOffer = offer;
                }
            }
            return acceptedOffer;
        } else {
            throw new RuntimeException("Order not Paid!");
        }
    }

    @Override
    //todo قرار دادن فیلتر برای سفارش ه
    public List<Order> ordersFilter(OrderFilterDTO orderDTO) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> orderCriteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = orderCriteriaQuery.from(Order.class);

        orderFilter(orderDTO, predicateList, criteriaBuilder, orderRoot);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        orderCriteriaQuery.select(orderRoot).where(predicates);
        return entityManager.createQuery(orderCriteriaQuery).getResultList();
    }

    //todo قرار دادن فیلتر روی این موارد
    private void orderFilter(OrderFilterDTO orderDTO, List<Predicate> predicateList,
                             CriteriaBuilder criteriaBuilder, Root<Order> orderRoot) {

        if (orderDTO.getAddressDto() != null) {
            String address = "..*.." + orderDTO.getAddressDto() + "..*..";
            predicateList.add(criteriaBuilder.like(orderRoot.get("..  Address dto .."), address));
        }

        if (orderDTO.getDescription() != null) {
            String description = "..*.." + orderDTO.getDescription() + "..*..";
            predicateList.add(criteriaBuilder.like(orderRoot.get("..comment.."), description));
        }

        if (orderDTO.getOrderStatus() != null) {
            predicateList.add(criteriaBuilder.equal(orderRoot.get("..orderStatus.."),
                    orderDTO.getOrderStatus()));
        }

        if (orderDTO.getSubServiceName() != null) {
            predicateList.add(criteriaBuilder.equal(orderRoot.get("..subServiceName.."),
                    orderDTO.getSubServiceName()));
        }

        if (orderDTO.getMinProposedPrice() == null && orderDTO.getMaxProposedPrice() != null) {
            predicateList.add(criteriaBuilder.lt(orderRoot.get("..proposedPrice.."),
                    orderDTO.getMaxProposedPrice()));
        }

        if (orderDTO.getMinProposedPrice() != null && orderDTO.getMaxProposedPrice() == null) {
            predicateList.add(criteriaBuilder.gt(orderRoot.get("..proposedPrice.."),
                    orderDTO.getMinProposedPrice()));
        }

        if (orderDTO.getMinProposedPrice() != null && orderDTO.getMaxProposedPrice() != null) {
            predicateList.add(criteriaBuilder.between(orderRoot.get("..proposedPrice.."),
                    orderDTO.getMinProposedPrice(),
                    orderDTO.getMaxProposedPrice()));
        }

        if (orderDTO.getMinCreationDate() != null && orderDTO.getMaxCreationDate() != null) {
            predicateList.add(criteriaBuilder.between(orderRoot.get("..creationDate.."),
                    LocalDateTime.parse(orderDTO.getMinCreationDate()),
                    LocalDateTime.parse(orderDTO.getMaxCreationDate())));
        }

        if (orderDTO.getMinUpdateTime() != null && orderDTO.getMaxUpdateTime() != null) {
            predicateList.add(criteriaBuilder.between(orderRoot.get("..updateTime.."),
                    LocalDateTime.parse(orderDTO.getMinUpdateTime()),
                    LocalDateTime.parse(orderDTO.getMaxUpdateTime())));
        }
    }
}
