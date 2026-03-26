package com.restaurant.repository;

import com.restaurant.models.OrderItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItemModel,Integer> {
}
