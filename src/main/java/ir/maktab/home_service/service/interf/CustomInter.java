package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.*;
import ir.maktab.home_service.dto.filter.CustomerFilterDto;

import java.util.List;

public interface CustomInter {

    Customer save(Customer customer);

    Customer update(Customer customer);

    Customer findByEmailAddress(String emailAddress);

    boolean existsByEmail(String emailAddress);

    int editPassword(Integer id, String newPassword);

    void addOrder(Integer customerId, Integer subServiceId, Order order);
    List<Offer> viewOrderOffersBaseOnExpertScore(Integer orderId);

    List<Offer> viewOrderOffersBaseOnProposedPrice(Integer orderId);

    Customer changePassword(Customer customer, String currentPassword, String newPassword);

    void setCommentForExpert(Integer orderId, Integer expertId, Comment comment);

    int changeOrderStatusAfterExpertComes(Integer id);

    int changeOrderStatusAfterStarted(Integer id);

    void updateCredit(Integer id, Long newCredit);

    void payFromCredit(Integer orderId, Integer customerId, Integer expertId, Long amount);

    List<Order> viewOrderHistory(Integer id);

    List<Customer> customersFilter(CustomerFilterDto customerDTO);


    List<Order> viewOrderHistory(Integer id, OrderStatus orderStatus);

    Long viewCredit(Integer id);

    void selectOffer(Integer id);

}
