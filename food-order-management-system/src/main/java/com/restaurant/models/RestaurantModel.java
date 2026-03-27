package com.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class RestaurantModel extends BaseModel{
    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<MenuItemModel> menuItem;


    private String restaurantName;
    private String location;


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<MenuItemModel> getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(List<MenuItemModel> menuItem) {
        this.menuItem = menuItem;
    }
}
