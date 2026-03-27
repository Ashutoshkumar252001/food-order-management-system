package com.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItemModel extends BaseModel {
    @ManyToOne
    private MenuItemModel menuItem;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

    private Integer quantity;
    private Double subtotal;

    public MenuItemModel getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemModel menuItem) {
        this.menuItem = menuItem;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}
