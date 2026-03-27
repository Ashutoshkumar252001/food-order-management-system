package com.restaurant.controllers;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.enums.OrderStatus;
import com.restaurant.models.OrderModel;
import com.restaurant.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<ResponseStructure<OrderModel>> placeOrder(@RequestBody OrderModel order){
       return orderService.placeOrder(order);

    }

    @GetMapping("/allOrders")
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<OrderModel>> getById (@PathVariable Integer id){
        return orderService.getById(id);
    }


    @GetMapping("/customer/{id}")
   public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderOfACustomer(@PathVariable Integer id){
        return orderService.getOrderOfACustomer(id);
    }

    @PutMapping("/updateOrder")
   public ResponseEntity<ResponseStructure<OrderModel>> updateOrderStatus(@RequestBody OrderModel order){
        return orderService.updateOrderStatus(order);
    }


    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<ResponseStructure<OrderModel>> cancelOrder(@PathVariable Integer id){
        return orderService.cancelOrder(id);
    }


    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderByStatus(@PathVariable OrderStatus status) {

        return orderService.getOrderByStatus(status);
    }


    @GetMapping("/date")
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderByDate(
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime orderDateTime) {

        return orderService.getOrderByDate(orderDateTime);
    }


    @GetMapping("/amount-range")
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderBetweenTotalAmountRange(
            @RequestParam Double minAmount,
            @RequestParam Double maxAmount) {

        return orderService.getOrderBetweenTotalAmountRange(minAmount, maxAmount);
    }


    @GetMapping("/restaurant")
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderOfParticularRestaurant(
        @RequestParam Integer restaurantId) {

      return orderService.getOrderOfParticularRestaurant(restaurantId);
}
}
