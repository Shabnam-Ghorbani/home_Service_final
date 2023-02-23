package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.enamiration.PersonStatus;
import ir.maktab.home_service.data.model.entity.Expert;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.dto.filter.ExpertFilterDTO;

import java.util.List;

public interface ExpInter {

    Expert save(Expert expert);

    int changePersonStatus(String emailAddress, PersonStatus personStatus);

    List<Order> displayOrdersForExpert(Integer id);

    int changeExpertActivation(Integer id, Boolean isActive);

    Expert findByEmailAddress(String emailAddress);

    List<Order> viewOrdersRelatedToExpert(Integer id);

    int updateScore(Integer id, Integer newScore);

    Double viewExpertScore(Integer id);

    Double viewExpertScoreFromCustomerComment(Integer id);

    void update(Expert expert);

    Expert updateExpert(Expert expert);
    public int calculateNumberOfPlacedOrders(Integer expertId, Boolean isAccept);

    public void addOfferForOrder(Integer expertId, Integer orderId, Offer offer);

    void updateCredit(Integer expertId, Long newCredit);

   // String signUpWithValidation(Expert expert, String imageName, Long imageSize);

    void signUp(Expert expert, String imageName, Long imageSize);


    void updateScore(Expert expert, Double instructionsScore);

    Expert findById(Integer id);

    Expert changePassword(Expert expert, String currentPassword, String newPassword);

    List<Offer> viewOfferHistory(Integer id, boolean isAccept);

    List<Order> viewOrderHistory(Integer id, boolean isAccept);

    List<Order> viewOrderHistory(Integer id, boolean isAccept, OrderStatus orderStatus);

    Long viewCredit(Integer id);

    List<Expert> expertsFilter(ExpertFilterDTO expertDTO);
}
