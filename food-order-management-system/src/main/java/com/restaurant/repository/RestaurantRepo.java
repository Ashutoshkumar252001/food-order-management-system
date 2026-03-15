package com.restaurant.repository;

import com.restaurant.models.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepo extends JpaRepository <RestaurantModel,Integer> {

    List<RestaurantModel> findRestaurantByLocation(String location);
    Optional<RestaurantModel> findByName(String name);
}
