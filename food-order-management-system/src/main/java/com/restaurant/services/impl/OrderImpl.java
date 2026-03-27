package com.restaurant.services.impl;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.enums.OrderStatus;
import com.restaurant.exceptions.IdNotFoundException;
import com.restaurant.models.*;
import com.restaurant.repository.*;
import com.restaurant.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private MenuItemRepo menuItemRepo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PaymentRepo paymentRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;


    @Override
    public ResponseEntity<ResponseStructure<OrderModel>> placeOrder(OrderModel order) {

        ResponseStructure<OrderModel> response = new ResponseStructure<>();

        if (order.getCustomer() == null || order.getCustomer().getId() == null) {
            throw new IdNotFoundException("Customer id is required");
        }

        Optional<CustomerModel> custOpt = customerRepo.findById(order.getCustomer().getId());

        if (custOpt.isEmpty()) {
            throw new IdNotFoundException("Customer not found");
        }

        CustomerModel customer = custOpt.get();

        if (order.getOrderItem() == null || order.getOrderItem().isEmpty()) {
            throw new IdNotFoundException("Order must contain at least one item");
        }

        if (order.getPayment() == null) {
            throw new IdNotFoundException("Payment is required");
        }

        double total = 0.0;

        for (OrderItemModel item : order.getOrderItem()) {

            Optional<MenuItemModel> menuOpt = menuItemRepo.findById(item.getMenuItem().getId());

            if (menuOpt.isEmpty()) {
                throw new IdNotFoundException("Menu item not found");
            }

            MenuItemModel menuItem = menuOpt.get();
            if (!menuItem.getAvailability()) {
                throw new RuntimeException("Menu item is currently unavailable");
            }

            item.setMenuItem(menuItem);

            double subtotal = menuItem.getPrice() * item.getQuantity();
            item.setSubtotal(subtotal);

            item.setOrder(order);

            total = total + subtotal;
        }
        order.setTotalAmount(total);
        order.setCustomer(customer);
        order.setOrderDateTime(LocalDateTime.now());
        order.setStatus(order.getStatus());

        PaymentModel payment = order.getPayment();


        payment.setOrder(order);
        order.setPayment(payment);

        payment.setAmount(total);
        payment.setPaymentStatus(payment.getPaymentStatus());
        payment.setPaymentMethod(payment.getPaymentMethod());




        OrderModel savedOrder = orderRepo.save(order);

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMsg("Order placed successfully with payment");
        response.setData(savedOrder);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getAllOrders() {
        ResponseStructure<List<OrderModel>> res = new ResponseStructure<>();
        List<OrderModel> li=orderRepo.findAll();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("all order are fetched");
        res.setData(li);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<OrderModel>> getById(Integer id) {
        Optional<OrderModel> opt = orderRepo.findById(id);

        ResponseStructure<OrderModel> res = new ResponseStructure<>();

        if (opt.isPresent()) {
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg(" Order is  found");
            res.setData(opt.get());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            res.setStatusCode(HttpStatus.NOT_FOUND.value());
            res.setMsg("Order is not found");
            res.setData(null);
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderOfACustomer(Integer id) {

        ResponseStructure<List<OrderModel>> res = new ResponseStructure<>();

        Optional<CustomerModel> opt = customerRepo.findById(id);

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Customer not found with id: " + id);
        }

        List<OrderModel> orders = orderRepo.findByCustomer_Id(id);

        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for this customer");
        }

        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Orders fetched successfully for customer");
        res.setData(orders);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<OrderModel>> updateOrderStatus(OrderModel order) {

        ResponseStructure<OrderModel> res = new ResponseStructure<>();

        if (order.getId() == null) {
            throw new IdNotFoundException("Order id must be provided");
        }

        Optional<OrderModel> opt = orderRepo.findById(order.getId());

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Order not found");
        }

        OrderModel existingOrder = opt.get();

        existingOrder.setStatus(order.getStatus());

        if (existingOrder.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Delivered order cannot be updated");
        }

        double totalAmount = 0.0;

        if (existingOrder.getOrderItem() == null || existingOrder.getOrderItem().isEmpty()) {
            throw new RuntimeException("Order has no items");
        }

        for (OrderItemModel item : existingOrder.getOrderItem()) {

            double price = item.getMenuItem().getPrice();
            double subTotal = price * item.getQuantity();

            item.setSubtotal(subTotal);

            totalAmount += subTotal;
        }

        existingOrder.setTotalAmount(totalAmount);

        OrderModel updatedOrder = orderRepo.save(existingOrder);

        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Order status updated successfully");
        res.setData(updatedOrder);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<OrderModel>> cancelOrder(Integer id) {

        Optional<OrderModel> opt = orderRepo.findById(id);
        ResponseStructure<OrderModel> res = new ResponseStructure<>();

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Order not found");
        }

        OrderModel order = opt.get();

        if (order.getStatus() != OrderStatus.PLACED) {

            throw new RuntimeException("Order cannot be cancelled after preparation started");
        }

        order.setStatus(OrderStatus.CANCELLED);

        OrderModel updatedOrder = orderRepo.save(order);

        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Order cancelled successfully");
        res.setData(updatedOrder);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderByStatus(OrderStatus status) {
        ResponseStructure<List<OrderModel>> res = new ResponseStructure<>();
        List<OrderModel> order = orderRepo.findByStatus(status);
        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Status Of all Orders");
        res.setData(order);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderByDate(LocalDateTime orderDateTime) {

        ResponseStructure<List<OrderModel>> res = new ResponseStructure<>();

        LocalDateTime start = orderDateTime.toLocalDate().atStartOfDay();

        LocalDateTime end = orderDateTime.toLocalDate().atTime(23, 59, 59);

        List<OrderModel> orders = orderRepo.findByOrderDateTimeBetween(start, end);

        if (orders.isEmpty()) {
            throw new RuntimeException("No orders found for this date");
        }

        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Orders fetched by date");
        res.setData(orders);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderBetweenTotalAmountRange(Double minAmount, Double maxAmount) {
        ResponseStructure<List<OrderModel>> res = new ResponseStructure<>();
        List<OrderModel> order = orderRepo.findByTotalAmountBetween(minAmount,maxAmount);
        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("get order between minAmount and maxAmount");
        res.setData(order);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<OrderModel>>> getOrderOfParticularRestaurant(Integer restaurantId) {
        ResponseStructure<List<OrderModel>> res = new ResponseStructure<>();

        List<OrderModel> orders = orderRepo.findOrdersByRestaurantId(restaurantId);
        if (orders.isEmpty()) {
            throw new IdNotFoundException("No orders found for this restaurant");
        }
        else {
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("fetched order of this particular restaurant ");
            res.setData(orders);
        }

        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
