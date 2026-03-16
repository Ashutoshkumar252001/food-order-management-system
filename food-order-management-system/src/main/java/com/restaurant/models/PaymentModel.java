package com.restaurant.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
@Entity
public class PaymentModel extends BaseModel{

    @OneToOne
    private OrderModel order;

    private String paymentMethod;
    private String paymentStatus;
    private Double amount;

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
