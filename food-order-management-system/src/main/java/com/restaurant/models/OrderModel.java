package com.restaurant.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDate;
import java.util.List;

@Entity
public class OrderModel extends BaseModel{

    @ManyToOne
    private CustomerModel customer;

    @OneToOne
    private PaymentModel payment;

    @OneToMany
    private List<OrderItemModel> orderItem;

    private LocalDate orderDateTime;
    private String Status;
    private Double totalAmount;

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public PaymentModel getPayment() {
        return payment;
    }

    public void setPayment(PaymentModel payment) {
        this.payment = payment;
    }

    public List<OrderItemModel> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemModel> orderItem) {
        this.orderItem = orderItem;
    }

    public LocalDate getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDate orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
