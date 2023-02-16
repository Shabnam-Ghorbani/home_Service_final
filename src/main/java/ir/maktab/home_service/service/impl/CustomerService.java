package ir.maktab.home_service.service.impl;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.*;
import ir.maktab.home_service.data.model.repository.*;
import ir.maktab.home_service.dto.filter.CustomerFilterDto;
import ir.maktab.home_service.exception.*;
import ir.maktab.home_service.service.interf.CustomInter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService implements CustomInter {
    private static final Logger logger = LoggerFactory.getLogger(ExpertService.class);
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OfferRepository offerRepository;
    private final CommentRepository commentRepository;
    private final ExpertRepository expertRepository;
    private final SubServiceRepository subServiceRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BaseServiceService baseServiceService;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Customer save(Customer customer) {
        Optional<Customer> foundedCustomer = customerRepository.findByEmailAddress(customer.getEmailAddress());
        if (foundedCustomer.isPresent()) {
            throw new EntityIsExistException("this emailAddress exist!");
        } else {
            return customerRepository.save(customer);
        }
    }
    @Override
    @Transactional
    public List<Offer> viewOrderOffersBaseOnExpertScore(Integer orderId) {
        List<Offer> offers = offerRepository.findOfferOrderExpertScore(orderId);
        if (offers.isEmpty())
            throw new NotFoundException("Unfortunately, no such offer was found");
        return offers;
    }

    @Override
    public Customer update(Customer customer) {

        return customerRepository.save(customer);
    }
    @Override
    public void addOrder(Integer customerId, Integer subServiceId, Order order) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new NotFoundException("Unfortunately, no such customer was found");
        Optional<SubService> subService = subServiceRepository.findById(subServiceId);
        if (subService.isEmpty())
            throw new NotFoundException("Unfortunately, no such subService was found");
        order.setOrderStatus(OrderStatus.WAITING_FOR_EXPERT_SUGGESTIONS);
        order.setCustomer(customer.get());
        order.setSubService(subService.get());
        orderRepository.save(order);
    }
    @Override
    public List<Offer> viewOrderOffersBaseOnProposedPrice(Integer orderId) {
        List<Offer> offers = offerRepository.findOrderBasedPrice(orderId);
        if (offers.isEmpty())
            throw new NotFoundException("Unfortunately, no such offer was found");
        return offers;
    }

    @Override
    public Customer findByEmailAddress(String emailAddress) {
        Optional<Customer> customer = customerRepository.findByEmailAddress(emailAddress);
        return customer.orElseThrow(() -> new EntityNotExistException("Unfortunately, no such emailAddress was found"));
    }

    @Override
    @Transactional
    public boolean existsByEmail(String emailAddress) {
        return customerRepository.existsByEmailAddress(emailAddress);
    }

    @Override
    @Transactional
    public int editPassword(Integer id, String newPassword) {
        if (customerRepository.findById(id).isEmpty())
            throw new NotFoundException("Unfortunately, no such customer was found");
        return customerRepository.editPassword(id, passwordEncoder.encode(newPassword));
    }


    @Override
    public void selectOffer(Integer id) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isEmpty())
            throw new NotFoundException(" Unfortunately, no such offer was found");
        if (offer.get().getIsAccept().equals(true))
            throw new InCorrectException(" offer is accept!");
        offerRepository.editOffer(id, true);

        Integer orderId = offer.get().getOrder().getId();
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new NotFoundException(" Unfortunately, no such order was found");
        if (!order.get().getOrderStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SELECTION))
            throw new StatusException("WAITING FOR EXPERT SELECTION!");
        orderRepository.changeOrderStatus(orderId, OrderStatus.WAITING_FOR_EXPERT_SELECTION,
                OrderStatus.WAITING_FOR_THE_EXPERT_TO_ARRIVE);
    }

    @Override
    public int changeOrderStatusAfterExpertComes(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new NotFoundException(" Unfortunately, no such order was found");
        if (!order.get().getOrderStatus().equals(OrderStatus.WAITING_FOR_THE_EXPERT_TO_ARRIVE))
            throw new StatusException(" status order is not yet WAITING_FOR_THE_EXPERT_TO_ARRIVE!");
        return orderRepository.changeOrderStatus(id, OrderStatus.WAITING_FOR_THE_EXPERT_TO_ARRIVE, OrderStatus.STARTED);
    }

    @Override
    public int changeOrderStatusAfterStarted(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty())
            throw new NotFoundException(" Unfortunately, no such order was found");
        if (!order.get().getOrderStatus().equals(OrderStatus.STARTED))
            throw new StatusException(" order is not yet STARTED!");
        return orderRepository.changeOrderStatus(id, OrderStatus.STARTED, OrderStatus.DONE);
    }

    @Override
    public Customer changePassword(Customer customer, String currentPassword, String newPassword) {
        String password = customer.getPassword();
        if (password.equals(currentPassword)) {
            customer.setPassword(newPassword);
            logger.info("Changes applied successfully");
            return update(customer);
        } else {
            throw new InCorrectException("password is wrong!");
        }
    }

    @Override
    public void setCommentForExpert(Integer orderId, Integer expertId, Comment comment) {
        if (comment.getScore() == null)
            throw new NullException("cannot score  null");
        if (comment.getComment() == null)
            throw new NullException(" Opinion cannot be null!");
        if (comment.getScore() < 0 && comment.getScore() > 6)
            throw new ScoreException("the expert score should be between 1 and 5!");
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty())
            throw new NullException("Unfortunately, no such order was found");
        Optional<Expert> expert = expertRepository.findById(expertId);
        if (expert.isEmpty())
            throw new EntityNotExistException("Unfortunately, no such expert was found");
        comment.setOrder(order.get());
        commentRepository.save(comment);
        expertRepository.updateScore(expertId, (int) (expert.get().getScore() + comment.getScore()));
    }

    @Override
    public void updateCredit(Integer id, Long newCredit) {

        customerRepository.updateCredit(id, newCredit);
    }

    @Override
    @Transactional
    public void payFromCredit(Integer orderId, Integer customerId, Integer expertId, Long amount) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty())
            throw new NotFoundException("Unfortunately, no such order was found");
        if (!order.get().getOrderStatus().equals(OrderStatus.DONE))
            throw new StatusException(" is not yet DONE!");
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new NotFoundException("Unfortunately, no such customer was found");
        if (customer.get().getCredit() < amount)
            throw new InsufficientFoundsException("The amount is insufficient");
        Optional<Expert> expert = expertRepository.findById(expertId);
        if (expert.isEmpty())
            throw new NotFoundException("Unfortunately, no such expert was found");
        orderRepository.changeOrderStatus(orderId, OrderStatus.DONE, OrderStatus.PAID);
        updateCredit(customerId, customer.get().getCredit() - amount);
        expertRepository.updateCredit(expertId, expert.get().getCredit() + (long) (amount * 0.7));
    }

    @Override
    public List<Order> viewOrderHistory(Integer id) {
        List<Order> orders = orderRepository.findByCustomer(id);
        if (orders.isEmpty())
            throw new EntityNotExistException("Unfortunately, no such order was found");
        return orders;
    }


    @Override
    public List<Order> viewOrderHistory(Integer id, OrderStatus orderStatus) {
        List<Order> orders = orderRepository.findByCustomerIdAndOrderStatus(id, orderStatus);
        if (orders.isEmpty())
            throw new EntityNotExistException("Unfortunately, no such order was found");
        return orders;
    }

    @Override
    public Long viewCredit(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new EntityNotExistException("Unfortunately, no such customer was found");
        return customer.get().getCredit();
    }

    @Override
    public List<Customer> customersFilter(CustomerFilterDto customerDTO) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> customerCriteriaQuery = criteriaBuilder.createQuery(Customer.class);
        Root<Customer> customerRoot = customerCriteriaQuery.from(Customer.class);

        createFilters(customerDTO, predicateList, criteriaBuilder, customerRoot);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        customerCriteriaQuery.select(customerRoot).where(predicates);
        return entityManager.createQuery(customerCriteriaQuery).getResultList();
    }

    private void createFilters(CustomerFilterDto customerDTO, List<Predicate> predicateList,
                               CriteriaBuilder criteriaBuilder, Root<Customer> customerRoot) {
        if (customerDTO.getFirstname() != null) {
            String firstname = " */ " + customerDTO.getFirstname() + " */ ";
            predicateList.add(criteriaBuilder.like(customerRoot.get(" firstname "), firstname));
        }
        if (customerDTO.getLastname() != null) {
            String lastname = " */ " + customerDTO.getLastname() + " */ ";
            predicateList.add(criteriaBuilder.like(customerRoot.get(" lastname "), lastname));
        }
        if (customerDTO.getEmail() != null) {
            String email = " */ " + customerDTO.getEmail() + " */ ";
            predicateList.add(criteriaBuilder.like(customerRoot.get(" email "), email));
        }
        if (customerDTO.getIsActive() != null) {
            predicateList.add(criteriaBuilder.equal(customerRoot.get(" isActive "), customerDTO.getIsActive()));
        }

        if (customerDTO.getMinCredit() == null && customerDTO.getMaxCredit() != null) {
            predicateList.add(criteriaBuilder.lt(customerRoot.get(" credit "), customerDTO.getMaxCredit()));
        }
        if (customerDTO.getMinCredit() != null && customerDTO.getMaxCredit() == null) {
            predicateList.add(criteriaBuilder.gt(customerRoot.get(" credit "), customerDTO.getMinCredit()));
        }
        if (customerDTO.getMinCredit() != null && customerDTO.getMaxCredit() != null) {
            predicateList.add(criteriaBuilder.between(customerRoot.get(" credit "),
                    customerDTO.getMinCredit(), customerDTO.getMaxCredit()));
        }


        if (customerDTO.getMinCreationDate() != null && customerDTO.getMaxCreationDate() != null) {
            predicateList
                    .add(criteriaBuilder
                            .between(customerRoot.get(" creationDate "),
                                    LocalDateTime.parse(customerDTO.getMinCreationDate()),
                                    LocalDateTime.parse(customerDTO.getMaxCreationDate())));
        }
    }
}
