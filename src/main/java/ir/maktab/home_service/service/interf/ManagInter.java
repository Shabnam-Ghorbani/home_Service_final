package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.entity.*;
import ir.maktab.home_service.dto.filter.CustomerFilterDto;
import ir.maktab.home_service.dto.filter.ExpertFilterDTO;
import ir.maktab.home_service.dto.filter.OrderFilterDTO;

import java.util.List;
import java.util.Set;

public interface ManagInter {

    void save(Manager manager);

    void addBaseService(BaseService baseService);

    int deleteBAseService(Integer id);

    List<BaseService> findAllBaseService();

    void addSubService(Integer id, SubService subService);

    int deleteSubService(Integer id);

    void addExpertToSubService(Integer id, Integer expertId);

    void deleteExpertFromSubService(Integer subServiceId, Integer expertId);

    Manager findByUsername(String username);

    int editSubServiceBasePriceAndDescription(Integer id, Long basePrice, String description);

    List<Expert> findAllExpert();

    int changeExpertStatus(Integer id, PersonStatus personStatus);

    boolean checkExpertDelayForDoingWork(Integer offerId);

    int changeExpertActivation(Integer id, Boolean isActive);

    Set<Expert> viewSubServiceExperts(Integer id);

    List<Order> viewExpertOrderHistory(Integer id, boolean isAccept, OrderStatus orderStatus);

    List<Order> viewCustomerOrderHistory(Integer id);

    List<Order> viewCustomerOrderHistory(Integer id, OrderStatus orderStatus);

    List<SubService> findAllSubService();

    List<Expert> expertsFilter(ExpertFilterDTO expertFilterDTO);

    List<Customer> customersFilter(CustomerFilterDto customerFilterDTO);

    List<Order> ordersFilter(OrderFilterDTO orderFilterDTO);

    List<Order> viewExpertOrderHistory(Integer id, boolean isAccept);

    int calculateNumberOfRegisteredOrders(Integer id);

    int calculateNumberOfRegisteredOrders(Integer id, OrderStatus orderStatus);

    int calculateNumberOfPlacedOrders(Integer id);

    int calculateNumberOfPlacedOrders(Integer expertId, Boolean isAccept);
}
