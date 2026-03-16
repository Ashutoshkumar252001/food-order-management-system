package com.restaurant.services;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.models.RestaurantModel;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantService {
    ResponseEntity<ResponseStructure<RestaurantModel>> addRestaurant(RestaurantModel restaurant);
    ResponseEntity<ResponseStructure<List<RestaurantModel>>> getAllRestaurant();
    ResponseEntity<ResponseStructure<RestaurantModel>> getRestaurantById(Integer id);
    ResponseEntity<ResponseStructure<RestaurantModel>> updateRestaurant(RestaurantModel restaurant);
    ResponseEntity<ResponseStructure<RestaurantModel>> deleteRestaurant(Integer id);
    ResponseEntity<ResponseStructure<List<RestaurantModel>>> getRestaurantByLocation(String location);
    ResponseEntity<ResponseStructure<RestaurantModel>> getByName(String name);
    ResponseEntity<ResponseStructure<Page<RestaurantModel>>> getRestaurantByPaginationAndSorting(int pageNo, int pageSize, String field);
}
