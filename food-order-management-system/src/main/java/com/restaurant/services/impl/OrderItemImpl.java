package com.restaurant.services.impl;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.exceptions.IdNotFoundException;
import com.restaurant.models.MenuItemModel;
import com.restaurant.models.OrderItemModel;
import com.restaurant.models.OrderModel;
import com.restaurant.repository.MenuItemRepo;
import com.restaurant.repository.OrderItemRepo;
import com.restaurant.repository.OrderRepo;
import com.restaurant.services.MenuItemService;
import com.restaurant.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemImpl implements OrderItemService {

    @Autowired
    private OrderItemRepo orderItemRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Override
    public ResponseEntity<ResponseStructure<OrderItemModel>> addItemToOrder(OrderItemModel orderItem) {

        if (orderItem.getQuantity() == null || orderItem.getQuantity() < 1) {
            throw new RuntimeException("Quantity must be at least 1");
        }

        if (orderItem.getOrder() == null || orderItem.getOrder().getId() == null) {
            throw new IdNotFoundException("Order id is required");
        }

        Integer orderId = orderItem.getOrder().getId();

        Optional<OrderModel> opt = orderRepo.findById(orderId);

        if (opt.isPresent()) {
            OrderModel order = opt.get();
            orderItem.setOrder(order);

            if (orderItem.getMenuItem() == null || orderItem.getMenuItem().getId() == null) {
                throw new RuntimeException("Menu item id is required");
            }

            Optional<MenuItemModel> opt1 = menuItemRepo.findById(orderItem.getMenuItem().getId());

            if (opt1.isPresent()) {
                MenuItemModel menu = opt1.get();

                double subtotal = menu.getPrice() * orderItem.getQuantity();
                orderItem.setMenuItem(menu);
                orderItem.setSubtotal(subtotal);

            } else {
                throw new IdNotFoundException("Menu item not found");
            }

            OrderItemModel savedItem = orderItemRepo.save(orderItem);

            ResponseStructure<OrderItemModel> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMsg("Item added to order successfully");
            res.setData(savedItem);

            return new ResponseEntity<>(res, HttpStatus.CREATED);

        } else {
            throw new IdNotFoundException("Order not found with id: " + orderId);
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<OrderItemModel>> updateQuantity(OrderItemModel orderItem) {

        if (orderItem.getId() == null) {
            throw new IdNotFoundException("OrderItem id is required");
        }

        if (orderItem.getQuantity() == null || orderItem.getQuantity() < 1) {
            throw new RuntimeException("Quantity must be at least 1");
        }

        Optional<OrderItemModel> opt = orderItemRepo.findById(orderItem.getId());

        if (opt.isPresent()) {

            OrderItemModel existingItem = opt.get();

            existingItem.setQuantity(orderItem.getQuantity());

            double price = existingItem.getMenuItem().getPrice();
            double subtotal = price * existingItem.getQuantity();

            existingItem.setSubtotal(subtotal);

            OrderItemModel updatedItem = orderItemRepo.save(existingItem);

            ResponseStructure<OrderItemModel> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Quantity updated successfully");
            res.setData(updatedItem);

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {
            throw new IdNotFoundException("OrderItem not found");
        }
    }
    @Override
    public ResponseEntity<ResponseStructure<OrderItemModel>> removeItem(Integer id) {

        Optional<OrderItemModel> opt = orderItemRepo.findById(id);

        if (opt.isPresent()) {
            OrderItemModel item = opt.get();
            orderItemRepo.delete(item);

            ResponseStructure<OrderItemModel> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Item removed successfully");
            res.setData(item);

            return new ResponseEntity<>(res,HttpStatus.OK);
        } else {
            throw new IdNotFoundException("OrderItem not found with id: " + id);
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<List<OrderItemModel>>> getItemByOrderId(Integer orderId) {

        Optional<OrderModel> opt = orderRepo.findById(orderId);

        if (opt.isPresent()) {
            OrderModel order = opt.get();

            List<OrderItemModel> items = order.getOrderItem();

            ResponseStructure<List<OrderItemModel>> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Order items fetched successfully");
            res.setData(items);

            return new ResponseEntity<>(res,HttpStatus.OK);
        } else {
            throw new IdNotFoundException("Order not found with id: " + orderId);
        }
    }
}