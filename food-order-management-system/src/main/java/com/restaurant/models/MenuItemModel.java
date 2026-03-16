package com.restaurant.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class MenuItemModel extends BaseModel{
    @OneToMany
    private List<OrderItemModel> orderItem;
    @ManyToOne
    private RestaurantModel restaurant;

    private String itemName;
    private Double price;
    private String availability;

    public List<OrderItemModel> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemModel> orderItem) {
        this.orderItem = orderItem;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
