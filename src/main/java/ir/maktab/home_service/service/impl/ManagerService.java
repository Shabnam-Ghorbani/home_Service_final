package ir.maktab.home_service.service.impl;


import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.entity.*;
import ir.maktab.home_service.data.model.repository.*;

import ir.maktab.home_service.dto.filter.CustomerFilterDto;
import ir.maktab.home_service.dto.filter.ExpertFilterDTO;
import ir.maktab.home_service.dto.filter.OrderFilterDTO;
import ir.maktab.home_service.exception.DuplicateConfirmException;
import ir.maktab.home_service.exception.EntityNotExistException;
import ir.maktab.home_service.exception.NotFoundException;
import ir.maktab.home_service.service.interf.ManagInter;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ManagerService implements ManagInter {
    private final ManagerRepository managerRepository;
    private final SubServiceRepository subServiceRepository;
    private final ExpertRepository expertRepository;
    private final OfferService offerService;
    private final OfferRepository offerRepository;
    private final ExpertService expertService;
    private final BaseServiceRepository baseServiceRepository;
    private final CustomerService customerService;
    private final OrderService orderService;
    private final CustomerRepository customerRepository;


    @Override
    public void save(Manager manager) {
        managerRepository.save(manager);
    }

    @Override
    public void addBaseService(BaseService baseService) {
        if (baseServiceRepository.findByName(baseService.getName()).isPresent())
            throw new NotFoundException("this base service  is exist!");
        baseServiceRepository.save(baseService);
    }

    @Override
    public int deleteBAseService(Integer id) {
        if (baseServiceRepository.findById(id).isEmpty())
            throw new NotFoundException("this base service dose not exist!");
        return baseServiceRepository.deleteBaseServiceById(id);
    }

    @Override
    public void addSubService(Integer id, SubService subService) {
        Optional<BaseService> baseService = baseServiceRepository.findById(id);
        if (baseService.isEmpty())
            throw new NotFoundException("this base service dose not exist!");
        if (subServiceRepository.findByName(subService.getName()).isPresent())
            throw new DuplicateConfirmException("this sub-service already exist!");
        subServiceRepository.save(subService);
    }

    @Override
    public int deleteSubService(Integer id) {
        if (subServiceRepository.findById(id).isEmpty())
            throw new NotFoundException("this sub-service dose not exist!");
        return subServiceRepository.deleteSubServiceById(id);
    }

    public Manager findByUsername(String username) {
        Optional<Manager> manager = managerRepository.findByUsername(username);
        return manager.orElseThrow(() -> new EntityNotExistException("username not exist!"));
    }

    @Transactional
    @Override
    public int editSubServiceBasePriceAndDescription(Integer id, Long basePrice, String description) {
        int affectedRows = subServiceRepository.editBasePriceAndDescription(id, basePrice, description);
        if (affectedRows == 0)
            throw new EntityNotExistException("this subService dose not exist!");
        return affectedRows;
    }

    @Override
    public List<Expert> findAllExpert() {
        List<Expert> experts = (List<Expert>) expertRepository.findAll();
        if (experts.isEmpty())
            throw new EntityNotExistException("there is no experts!");
        return experts;
    }

    @Transactional
    @Override
    public int changeExpertStatus(Integer id, PersonStatus personStatus) {
        if (expertRepository.findById(id).isEmpty())
            throw new EntityNotExistException("this expert does not exist!");
        return expertRepository.changePersonStatus(String.valueOf(id), personStatus);
    }

    @Override
    public boolean checkExpertDelayForDoingWork(Integer offerId) {
        Optional<Offer> offer = offerRepository.findById(offerId);
        if (offer.isEmpty())
            throw new EntityNotExistException("there is no offers!");
        Order order = offer.get().getOrder();
        if (!order.getOrderStatus().equals(OrderStatus.PAID))
            throw new NotFoundException(" order is not yet PAID!");
        Optional<Expert> expert = expertRepository.findById(offer.get().getExpert().getId());
        if (expert.isEmpty())
            throw new EntityNotExistException("this expert does not exist!");
        if (order.getUpdateTime().compareTo(offer.get().getEndTime()) > 0) {
            long timeDifference = ChronoUnit.HOURS.between(offer.get().getEndTime(), order.getUpdateTime());
            expert.get().setScore(expert.get().getScore() - (int) timeDifference);
            expertRepository.save(expert.get());
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public int changeExpertActivation(Integer id, Boolean isActive) {
        Optional<Expert> expert = expertRepository.findById(id);
        if (expert.isEmpty())
            throw new EntityNotExistException("this expert does not exist!");
        if (expert.get().getScore() > 0 | expert.get().getIsActive().equals(false))
            throw new EntityNotExistException("the expert score is either positive or the expert account is inactive");
        return expertRepository.changeExpertActivation(id, isActive);
    }

    @Override
    public Set<Expert> viewSubServiceExperts(Integer id) {
        Optional<SubService> subService = subServiceRepository.findById(id);
        if (subService.isEmpty()) {
            throw new EntityNotExistException("this sub-service dose not exist!");
        }
        return subService.get().getExperts();
    }

    @Override
    public List<Order> viewExpertOrderHistory(Integer id, boolean isAccept, OrderStatus orderStatus) {
        return expertService.viewOrderHistory(id, isAccept, orderStatus);
    }

    @Override
    public List<Order> viewExpertOrderHistory(Integer id, boolean isAccept) {
        return expertService.viewOrderHistory(id, isAccept);
    }

    @Override
    public List<Order> viewCustomerOrderHistory(Integer id) {
        return customerService.viewOrderHistory(id);
    }

    @Override
    public List<Order> viewCustomerOrderHistory(Integer id, OrderStatus orderStatus) {
        return customerService.viewOrderHistory(id, orderStatus);
    }

    @Override
    public void addExpertToSubService(Integer id, Integer expertId) {
        Optional<SubService> subService = subServiceRepository.findById(id);
        if (subService.isEmpty()) {
            throw new NotFoundException("this subService dose not exist!");
        }
        Optional<Expert> expert = expertRepository.findById(expertId);
        if (expert.isEmpty()) {
            throw new NotFoundException("this expert does not exist!");
        } else if (!expert.get().getPersonStatus().equals(PersonStatus.CONFIRMED)) {
            throw new EntityNotExistException(" expert is not confirmed");
        }
        expert.get().addSubService(subService.get());
        expertRepository.save(expert.get());
    }

    @Override
    public void deleteExpertFromSubService(Integer subServiceId, Integer expertId) {
        Optional<SubService> subService = subServiceRepository.findById(subServiceId);
        if (subService.isEmpty()) {
            throw new NotFoundException("this subService dose not exist!");
        }
        Optional<Expert> expert = expertRepository.findById(expertId);
        if (expert.isEmpty()) {
            throw new NotFoundException("this expert does not exist!");
        }
        expert.get().deleteSubService(subService.get());
        expertRepository.save(expert.get());
    }

    @Override
    public List<BaseService> findAllBaseService() {
        List<BaseService> baseServices = (List<BaseService>) baseServiceRepository.findAll();
        if (baseServices.isEmpty())
            throw new NotFoundException("there is no baseServices");
        return baseServices;
    }

    @Override
    public List<SubService> findAllSubService() {
        List<SubService> subServices = (List<SubService>) subServiceRepository.findAll();
        if (subServices.isEmpty())
            throw new NotFoundException("there is no subServices");
        return subServices;
    }

    @Override
    public List<Expert> expertsFilter(ExpertFilterDTO expertFilterDTO) {
        return expertService.expertsFilter(expertFilterDTO);
    }

    @Override
    public List<Customer> customersFilter(CustomerFilterDto customerFilterDTO) {
        return customerService.customersFilter(customerFilterDTO);
    }

    @Override
    public List<Order> ordersFilter(OrderFilterDTO orderFilterDTO) {
        return orderService.ordersFilter(orderFilterDTO);
    }

    @Override
    public int calculateNumberOfRegisteredOrders(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new NotFoundException("this customer does not exit!");
        return orderService.NumberOfRegisteredOrders(id);
    }

    @Override
    public int calculateNumberOfRegisteredOrders(Integer id, OrderStatus orderStatus) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new NotFoundException("this customer does not exit!");
        return orderService.NumberOfRegisteredOrders(id, orderStatus);
    }

    @Override
    public int calculateNumberOfPlacedOrders(Integer id) {
        Optional<Expert> expert = expertRepository.findById(id);
        if (expert.isEmpty())
            throw new NotFoundException("this expert does not exist!");
       return offerService.calculateNumberOfRegisteredOffers(id);

    }
    @Override
    public int calculateNumberOfPlacedOrders(Integer expertId, Boolean isAccept) {
        Optional<Expert> expert = expertRepository.findById(expertId);
        if (expert.isEmpty())
            throw new NotFoundException("this expert does not exist!");
        return offerService.calculateNumberOfRegisteredOffers(expertId, isAccept);
    }
}
