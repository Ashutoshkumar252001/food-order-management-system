package com.restaurant.services.impl;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.exceptions.IdNotFoundException;
import com.restaurant.exceptions.NoRecordAvailableException;
import com.restaurant.models.RestaurantModel;
import com.restaurant.repository.RestaurantRepo;
import com.restaurant.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class RestaurantImpl implements RestaurantService {
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Override
    public ResponseEntity<ResponseStructure<RestaurantModel>> addRestaurant(RestaurantModel restaurant) {
        {
            ResponseStructure<RestaurantModel> res = new ResponseStructure<>();
            RestaurantModel r = restaurantRepo.save(restaurant);
            res.setStatusCode(HttpStatus.CREATED.value());
            res.setMsg("restaurant  is add");
            res.setData(r);
            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }
    }

    @Override
    public ResponseEntity<ResponseStructure<List<RestaurantModel>>> getAllRestaurant() {
        ResponseStructure<List<RestaurantModel>> res = new ResponseStructure<>();
        List<RestaurantModel> rest = restaurantRepo.findAll();
        res.setStatusCode(HttpStatus.CONTINUE.value());
        res.setMsg("Fetches all restaurant records");
        res.setData(rest);


        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<RestaurantModel>> getRestaurantById(Integer id) {
        ResponseStructure<RestaurantModel> res = new ResponseStructure<>();
        Optional<RestaurantModel> opt = restaurantRepo.findById(id);
        if(opt.isPresent())
        {
            RestaurantModel r = opt.get();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("restaurant is available");
            res.setData(r);
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        else
        {
            throw new IdNotFoundException("Id is not Available");
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<RestaurantModel>> updateRestaurant(RestaurantModel restaurant) {
        ResponseStructure<RestaurantModel> res = new ResponseStructure<>();
        if (restaurant.getId()==null)
        {
            throw new IdNotFoundException("id must be passed ");
        }
        Optional<RestaurantModel>opt = restaurantRepo.findById(restaurant.getId());
        if(opt.isPresent())
        {
            RestaurantModel existing = opt.get();

            existing.setRestaurantName(restaurant.getRestaurantName());
            existing.setLocation(restaurant.getLocation());
            RestaurantModel r=restaurantRepo.save(existing);

            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Restaurant updated successfully");
            res.setData(r);
            return new ResponseEntity<>(res,HttpStatus.OK);

        }
        else
        {
            throw new IdNotFoundException("ID not found in database");
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<RestaurantModel>> deleteRestaurant(Integer id) {
        ResponseStructure<RestaurantModel> res = new ResponseStructure<>();
        Optional<RestaurantModel> opt = restaurantRepo.findById(id);
        if(opt.isPresent())
        {
            RestaurantModel r = opt.get();
            restaurantRepo.delete(r);
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Restaurant is deleted");
            res.setData(r);
            return new ResponseEntity<>(res,HttpStatus.OK);

        }
        throw new IdNotFoundException("Id is not available in database");
    }

    @Override
    public ResponseEntity<ResponseStructure<List<RestaurantModel>>> getRestaurantByLocation(String location) {
        ResponseStructure<List<RestaurantModel>> res = new ResponseStructure<>();
        List<RestaurantModel> li = restaurantRepo.findRestaurantByLocation(location);
        if (li.isEmpty()) {
            throw new NoRecordAvailableException("");
        } else {
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Hotel is found at this place");
            res.setData(li);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<ResponseStructure<RestaurantModel>> getByName(String name) {
        ResponseStructure<RestaurantModel> res = new ResponseStructure<>();
        Optional<RestaurantModel> opt = restaurantRepo.getByName(name);
        if(opt.isPresent()){
            RestaurantModel r = opt.get();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Restaurant is available");
            res.setData(r);
            return new ResponseEntity<>(res,HttpStatus.OK);
        }else {
            throw new NoRecordAvailableException("");
        }


    }

    @Override
    public ResponseEntity<ResponseStructure<Page<RestaurantModel>>> getRestaurantByPaginationAndSorting(int pageNo, int pageSize, String field) {
        ResponseStructure<Page<RestaurantModel>> res = new ResponseStructure<>();
        PageRequest pageRequest  = PageRequest.of(pageNo,pageSize, Sort.by(field));
        Page<RestaurantModel> page = restaurantRepo.findAll(pageRequest);

        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Restaurant fetched with pagination and sorting");
        res.setData(page);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
