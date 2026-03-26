package com.restaurant.services;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.enums.OrderStatus;
import com.restaurant.models.OrderModel;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
 public ResponseEntity<ResponseStructure<OrderModel>> placeOrder(OrderModel order);
 public ResponseEntity<ResponseStructure<List<OrderModel>>> getAllOrders();
 public ResponseEntity<ResponseStructure<OrderModel>> getById (Integer id);
 public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderOfACustomer(Integer id);
 public ResponseEntity<ResponseStructure<OrderModel>> updateOrderStatus(OrderModel order);
 public ResponseEntity<ResponseStructure<OrderModel>> cancelOrder(Integer id);
 public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderByStatus(OrderStatus status);
 public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderByDate(LocalDateTime orderDateTime);
 public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderBetweenTotalAmountRange(Double minAmount,Double maxAmount);
 public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderOfParticularRestaurant(Integer restaurantId);

}
