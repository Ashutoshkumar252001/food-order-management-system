package com.restaurant.controllers;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.models.RestaurantModel;
import com.restaurant.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add")
    public ResponseEntity<ResponseStructure<RestaurantModel>> addRestaurant(@RequestBody RestaurantModel restaurant){
        return restaurantService.addRestaurant(restaurant);
    }

    @GetMapping("/rest/getAll")
    public ResponseEntity<ResponseStructure<List<RestaurantModel>>> getAllRestaurant(){
        return restaurantService.getAllRestaurant();
    }

    @GetMapping("/rest/id/{id}")
    public ResponseEntity<ResponseStructure<RestaurantModel>> getRestaurantById(@PathVariable Integer id){
        return restaurantService.getRestaurantById(id);

    }

    @PutMapping("/rest/update")
    public ResponseEntity<ResponseStructure<RestaurantModel>> updateRestaurant(@RequestBody  RestaurantModel restaurant){
        return restaurantService.updateRestaurant(restaurant);
    }

    @DeleteMapping("/rest/{id}")
    public ResponseEntity<ResponseStructure<RestaurantModel>> deleteRestaurant(@PathVariable Integer id){
        return restaurantService.deleteRestaurant(id);
    }
    @GetMapping("/rest/{location}")
    public ResponseEntity<ResponseStructure<List<RestaurantModel>>> getRestaurantByLocation(@PathVariable String location){
        return restaurantService.getRestaurantByLocation(location);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ResponseStructure<RestaurantModel>> getByName(@PathVariable String name){
        return restaurantService.getByName(name);
    }

    @GetMapping("/page/{pageNo}/{pageSize}/{field}")
    public ResponseEntity<ResponseStructure<Page<RestaurantModel>>> getEmpByPaginationAndSorting(
            @PathVariable int pageNo,
            @PathVariable int pageSize,
            @PathVariable String field){

        return restaurantService.getRestaurantByPaginationAndSorting(pageNo,pageSize,field);
    }
}
