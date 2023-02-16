package ir.maktab.home_service.data.model.repository;

import ir.maktab.home_service.data.model.enamiration.OrderStatus;
import ir.maktab.home_service.data.model.entity.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer> {

    List<Order> findByCustomer(Integer id);

    List<Order> findByCustomerIdAndOrderStatus(Integer id, OrderStatus orderStatus);


    @Query("select count(o.customer.id) from Order o where o.customer.id = :id ")
    int NumberOfRegisteredOrders(Integer id);

    @Query(" select count(o.customer.id) from Order o where o.customer.id = :id and o.orderStatus = :orderStatus ")
    int NumberOfRegisteredOrders(Integer id, OrderStatus orderStatus);


    @Query("from Order o where o.subService.id = :id and (o.orderStatus = :orderStatusOne or o.orderStatus = :orderStatusTwo)")
    List<Order> findBySubServiceIdAndOrderStatus(Integer id, OrderStatus orderStatusOne, OrderStatus orderStatusTwo);

    @Modifying
    @Query("update Order o set o.orderStatus = :newOrderStatus where o.id = :id and o.orderStatus = :orderStatus")
    int changeOrderStatus(Integer id, OrderStatus orderStatus, OrderStatus newOrderStatus);

    @Query("from Order o where o.subService.id = :id and (o.orderStatus = :orderStatusOne or o.orderStatus = :orderStatusTwo)")
    List<Order> findSubServiceIdAndOrderStatus(Integer id, OrderStatus orderStatusOne, OrderStatus orderStatusTwo);
}
