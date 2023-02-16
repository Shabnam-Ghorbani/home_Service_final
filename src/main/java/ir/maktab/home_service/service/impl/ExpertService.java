package ir.maktab.home_service.service.impl;


import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.enamiration.RoleStatus;
import ir.maktab.home_service.data.model.entity.*;
import ir.maktab.home_service.data.model.repository.*;
import ir.maktab.home_service.dto.filter.ExpertFilterDTO;
import ir.maktab.home_service.exception.*;
import ir.maktab.home_service.service.interf.ExpInter;
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
public class ExpertService implements ExpInter {
    private static final Logger logger = LoggerFactory.getLogger(ExpertService.class);
    private final ExpertRepository expertRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final CommentRepository commentRepository;
    private final OfferRepository offerRepository;
    private final OrderRepository orderRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Expert save(Expert expert) {
        Optional<Expert> foundedExpert = expertRepository.findByEmailAddress(expert.getEmailAddress());
        if (foundedExpert.isPresent()) {
            throw new EntityIsExistException("this emailAddress exist!");
        } else {
            return expertRepository.save(expert);
        }
    }
    @Override
    public void signUp(Expert expert, String imageName, Long imageSize) {
        if (expertRepository.findByEmailAddress(expert.getEmailAddress()).isPresent())
            throw new InvalidEmailException("this email already exist!");
        if (expertRepository.findById(expert.getId()).isPresent())
            throw new InCorrectException("this id already exist!");
        Validation.checkImage(imageName, imageSize);

        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setPersonStatus(PersonStatus.NEW);
        expert.setRoleStatus(RoleStatus.ROLE_EXPERT);
        expert.setIsActive(false);
        expert.setCredit(0L);
        expert.setScore((double) 0);
        save(expert);
    }

    @Transactional
    @Override
    public int changePersonStatus(String emailAddress, PersonStatus personStatus) {
        return expertRepository.changePersonStatus(emailAddress, personStatus);
    }
    @Override
    public void addOfferForOrder(Integer expertId, Integer orderId, Offer offer) {
        Optional<Expert> expert = expertRepository.findById(expertId);
        if (expert.isEmpty())
            throw new NotFoundException("this expert does not exist!");
        if (!expert.get().getPersonStatus().equals(PersonStatus.CONFIRMED))
            throw new NotFoundException(" expert is not confirmed");
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty())
            throw new NotFoundException("there is no order!");

        if (!expert.get().getServices().contains(order.get().getSubService()))
            throw new EntityNotExistException("this expert is not related to this subService!");
        if (order.get().getSubService().getBasePrice() > offer.getProposedPrice())
            throw new InCorrectException("the proposed-price should not be lower than the basePrice!");
        if (!order.get().getOrderStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTIONS))
            throw new EntityNotExistException(" this order not WAITING FOR EXPERT SUGGESTION!");

        offer.setIsAccept(false);
        offer.setExpert(expert.get());
        offer.setOrder(order.get());
        offerRepository.save(offer);
        orderRepository.changeOrderStatus(orderId, OrderStatus.WAITING_FOR_EXPERT_SUGGESTIONS, OrderStatus.WAITING_FOR_EXPERT_SELECTION);
    }


    @Override
    public List<Order> displayOrdersForExpert(Integer id) {
        List<Order> orders = new ArrayList<>();
        Optional<Expert> expert = expertRepository.findById(id);
        if (expert.isEmpty())
            throw new EntityNotExistException("this expert does not exist!");
        expert.get().getServices().forEach(subService -> {
            List<Order> filteredOrder = orderRepository
                    .findBySubServiceIdAndOrderStatus(
                            subService.getId(),
                            OrderStatus.WAITING_FOR_EXPERT_SELECTION,
                            OrderStatus.WAITING_FOR_EXPERT_SUGGESTIONS);
            orders.addAll(filteredOrder);
        });
        return orders;
    }

    @Transactional
    @Override
    public int changeExpertActivation(Integer id, Boolean isActive) {
        return expertRepository.changeExpertActivation(id, isActive);
    }

    @Override
    public Expert findByEmailAddress(String emailAddress) {
        Optional<Expert> expert = expertRepository.findByEmailAddress(emailAddress);
        return expert.orElseThrow(() -> new EntityNotExistException("emailAddress not exist!"));
    }

    @Override
    public Expert updateExpert(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public int calculateNumberOfPlacedOrders(Integer expertId, Boolean isAccept) {
        return 0;
    }

    @Override
    public void updateCredit(Integer expertId, Long newCredit) {
        expertRepository.updateCredit(expertId, newCredit);
    }

    @Override
    public void update(Expert expert) {
        expertRepository.save(expert);
    }


    @Override
    public void updateScore(Expert expert, Double instructionsScore) {
        Double expertScore = expert.getScore();
        Double newScore = (expertScore + instructionsScore) / 2;
        expert.setScore(newScore);
        update(expert);
    }

    @Override
    @Transactional
    public int updateScore(Integer id, Integer newScore) {

        return expertRepository.updateScore(id, newScore);
    }

    @Override
    public Double viewExpertScore(Integer id) {
        Optional<Expert> expert = expertRepository.findById(id);
        if (expert.isEmpty())
            throw new NotFoundException("this expert does not exist!");
        return expert.get().getScore();
    }

    @Override
    public Double viewExpertScoreFromCustomerComment(Integer id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isEmpty())
            throw new NotFoundException("this comment does not exist!");
        return comment.get().getScore();
    }

    @Override
    public Expert findById(Integer id) {
        Optional<Expert> expert = expertRepository.findById(id);
        return expert.orElseThrow(() -> new EntityNotExistException("expert not exist!"));
    }

    @Override
    public Expert changePassword(Expert expert, String currentPassword, String newPassword) {
        String password = expert.getPassword();
        if (password.equals(currentPassword)) {
            expert.setPassword(newPassword);
            logger.info("your password change successfully");
            return updateExpert(expert);
        } else {
            throw new InCorrectException("password is wrong!");
        }
    }
    @Override
    public List<Offer> viewOfferHistory(Integer id, boolean isAccept) {
        Optional<Expert> expert = expertRepository.findById(id);
        if (expert.isEmpty())
            throw new EntityNotExistException("this expert does not exist!");
        return offerRepository.findOfferByExpertIdAndIsAccept(id, isAccept);
    }

    @Override
    public List<Order> viewOrderHistory(Integer id, boolean isAccept) {
        List<Order> orders = new ArrayList<>();
        List<Offer> expertOffers = viewOfferHistory(id, isAccept);
        expertOffers.forEach(offer ->
                orders.add(offer.getOrder()));
        return orders;
    }

    @Override
    public List<Order> viewOrderHistory(Integer id, boolean isAccept, OrderStatus orderStatus) {
        List<Order> orders = new ArrayList<>();
        List<Offer> expertOffers = viewOfferHistory(id, isAccept);
        expertOffers.stream()
                .filter(offer -> offer.getOrder().getOrderStatus().equals(orderStatus))
                .forEach(offer -> {
                    orders.add(offer.getOrder());
                });
        return orders;
    }

    @Override
    public Long viewCredit(Integer id) {
        Optional<Expert> expert = expertRepository.findById(id);
        if (expert.isEmpty())
            throw new EntityNotExistException("this expert does not exist!");
        return expert.get().getCredit();
    }

    //ToDo مشاهده سفارشات مربوط به کارشناس
    @Override
    public List<Order> viewOrdersRelatedToExpert(Integer id) {
        List<Order> orders = new ArrayList<>();
        Optional<Expert> expert = expertRepository.findById(id);
        if (expert.isEmpty())
            throw new NotFoundException("this expert does not exist!");
        expert.get().getServices().forEach(subService -> {
            List<Order> expertOrder = orderRepository
                    .findBySubServiceIdAndOrderStatus(
                            subService.getId(),
                            OrderStatus.WAITING_FOR_EXPERT_SELECTION,
                            OrderStatus.WAITING_FOR_EXPERT_SUGGESTIONS);
            orders.addAll(expertOrder);
        });
        return orders;
    }

    @Override
    @Transactional
    public List<Expert> expertsFilter(ExpertFilterDTO expertDTO) {
        List<Predicate> predicateList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expert> expertCriteriaQuery = criteriaBuilder.createQuery(Expert.class);
        Root<Expert> expertRoot = expertCriteriaQuery.from(Expert.class);

        createFilters(expertDTO, predicateList, criteriaBuilder, expertRoot);
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicateList.toArray(predicates);
        expertCriteriaQuery.select(expertRoot).where(predicates);
        return entityManager.createQuery(expertCriteriaQuery).getResultList();
    }

    private void createFilters(ExpertFilterDTO filter, List<Predicate> predicateList, CriteriaBuilder criteriaBuilder,
                               Root<Expert> expertRoot) {
        if (filter.getFirstname() != null) {
            String firstname = " ** " + filter.getFirstname() + " ** ";
            predicateList.add(criteriaBuilder.like(expertRoot.get("firstname"), firstname));
        }
        if (filter.getLastname() != null) {
            String lastname = " ** " + filter.getLastname() + " ** ";
            predicateList.add(criteriaBuilder.like(expertRoot.get("lastname"), lastname));
        }
        if (filter.getEmailAddress() != null) {
            String email = " ** " + filter.getEmailAddress() + " ** ";
            predicateList.add(criteriaBuilder.like(expertRoot.get(" email "), email));

            if (filter.getIsActive() != null) {
                predicateList.add(criteriaBuilder.equal(expertRoot.get(" isActive "), filter.getIsActive()));
            }
            if (filter.getPersonStatus() != null) {
                predicateList.add(criteriaBuilder.equal(expertRoot.get(" personStatus "), filter.getPersonStatus()));
            }

            if (filter.getMinScore() == null
                    && filter.getMaxScore() != null) {
                predicateList.add(criteriaBuilder.lt(expertRoot.get(" score "), filter.getMaxScore()));
            }
            if (filter.getMinScore() != null
                    && filter.getMaxScore() == null) {
                predicateList.add(criteriaBuilder.gt(expertRoot.get(" score "), filter.getMinScore()));
            }
            if (filter.getMinScore() != null
                    && filter.getMaxScore() != null) {
                predicateList.add(criteriaBuilder.between(expertRoot.get(" score "),
                        filter.getMinScore(), filter.getMaxScore()));
            }

            if (filter.getMinCredit() == null
                    && filter.getMaxCredit() != null) {
                predicateList.add(criteriaBuilder.lt(expertRoot.get(" credit/ "), filter.getMaxCredit()));
            }
            if (filter.getMinCredit() != null &&
                    filter.getMaxCredit() == null) {
                predicateList.add(criteriaBuilder.gt(expertRoot.get(" credit /"), filter.getMinCredit()));
            }
            if (filter.getMinCredit() != null &&
                    filter.getMaxCredit() != null) {
                predicateList.add(criteriaBuilder.between(expertRoot.get(" credit  / "),
                        filter.getMinCredit(), filter.getMaxCredit()));
            }

            if (filter.getMinCreationDate() != null
                    && filter.getMaxCreationDate() != null) {
                predicateList
                        .add(criteriaBuilder
                                .between(expertRoot.get("creationDate"),
                                        LocalDateTime.parse(filter.getMinCreationDate()),
                                        LocalDateTime.parse(filter.getMaxCreationDate())));
            }
        }
    }
}

