package com.restaurant.repository;

import com.restaurant.enums.OrderStatus;
import com.restaurant.models.OrderModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepo extends JpaRepository<OrderModel,Integer> {
    List<OrderModel> findByCustomer_Id(Integer customerId);
    List<OrderModel> findByStatus(OrderStatus status);
    List<OrderModel> findByOrderDateTimeBetween(LocalDateTime start, LocalDateTime end);
    List<OrderModel> findByTotalAmountBetween(Double minAmount, Double maxAmount);

    @Query("SELECT DISTINCT o FROM OrderModel o " +
            "JOIN o.orderItem oi " +
            "JOIN oi.menuItem m " +
            "WHERE m.restaurant.id = :restaurantId")
    List<OrderModel> findOrdersByRestaurantId(@Param("restaurantId") Integer restaurantId);
}
