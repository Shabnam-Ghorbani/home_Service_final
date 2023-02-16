package ir.maktab.home_service.service.interf;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.Offer;
import ir.maktab.home_service.data.model.entity.Order;
import ir.maktab.home_service.dto.filter.OrderFilterDTO;

import java.util.List;

public interface OrdInter {

    Order save(Order order);

    List<Order> findByCustomer(Integer id);

    Order findById(Integer id);

    List<Order> findBySubServiceIdAndOrderStatus(Integer id, OrderStatus orderStatusOne, OrderStatus orderStatusTwo);

    int changeOrderStatus(Integer id, OrderStatus orderStatus, OrderStatus newOrderStatus);
    public List<Order> findByCustomerIdAndOrderStatus(Integer customerId, OrderStatus orderStatus);

    List<Order> findSubServiceIdAndOrderStatus(Integer id, OrderStatus orderStatusOne, OrderStatus orderStatusTwo);

    int NumberOfRegisteredOrders(Integer id);

    int NumberOfRegisteredOrders(Integer id, OrderStatus orderStatus);

    Offer findAcceptedOfferOfOrder(Order order);

    List<Order> ordersFilter(OrderFilterDTO orderDTO);
}
