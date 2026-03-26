package com.restaurant.controllers;


import com.restaurant.dto.ResponseStructure;
import com.restaurant.models.OrderItemModel;
import com.restaurant.services.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order-item")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping
    public ResponseEntity<ResponseStructure<OrderItemModel>> addItemToOrder(
            @RequestBody OrderItemModel orderItem) {

        return orderItemService.addItemToOrder(orderItem);
    }

    @PutMapping("/quantity")
    public ResponseEntity<ResponseStructure<OrderItemModel>> updateQuantity(
            @RequestBody OrderItemModel orderItem) {

        return orderItemService.updateQuantity(orderItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<OrderItemModel>> removeItem(
            @PathVariable Integer id) {

        return orderItemService.removeItem(id);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<ResponseStructure<List<OrderItemModel>>> getItemByOrderId(
            @PathVariable Integer orderId) {

        return orderItemService.getItemByOrderId(orderId);
    }
}
