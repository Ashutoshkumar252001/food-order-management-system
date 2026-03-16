package com.restaurant.repository;

import com.restaurant.models.MenuItemModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepo extends JpaRepository<MenuItemModel,Integer> {

    List<MenuItemModel> findByPriceGreaterThan(double price);
    @Query("SELECT m FROM MenuItemModel m WHERE m.itemName = :itemName")
    Optional<MenuItemModel> getItemByName(@Param("itemName") String itemName);
}
