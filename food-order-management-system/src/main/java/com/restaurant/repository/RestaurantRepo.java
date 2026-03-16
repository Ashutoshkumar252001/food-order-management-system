package com.restaurant.repository;

import com.restaurant.models.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepo extends JpaRepository <RestaurantModel,Integer> {

    List<RestaurantModel> findRestaurantByLocation(String location);

    @Query("SELECT r FROM RestaurantModel r WHERE r.restaurantName = :restaurantName")
    Optional<RestaurantModel> getByName(@Param("restaurantName") String restaurantName);
}
