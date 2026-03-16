package com.restaurant.services.impl;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.exceptions.IdNotFoundException;
import com.restaurant.models.MenuItemModel;
import com.restaurant.models.RestaurantModel;
import com.restaurant.repository.MenuItemRepo;
import com.restaurant.repository.RestaurantRepo;
import com.restaurant.services.MenuItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuItemImpl implements MenuItemService {

    @Autowired
    private MenuItemRepo menuItemRepo;
    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> addMenuItems(List<MenuItemModel> menu) {


        ResponseStructure<List<MenuItemModel>> res = new ResponseStructure<>();
        Integer restaurantId = menu.get(0).getRestaurant().getId();
        Optional<RestaurantModel> opt = restaurantRepo.findById(restaurantId);
        if (opt.isEmpty()){
            throw new IdNotFoundException("Restaurant not found. Menu items cannot be saved");

        }
        List<MenuItemModel> savedItems = menuItemRepo.saveAll(menu);


        res.setStatusCode(HttpStatus.CREATED.value());
        res.setMsg("Menu items added successfully");
        res.setData(savedItems);

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> getAllMenuItems(List<MenuItemModel> menu) {

        List<MenuItemModel> items = menuItemRepo.findAll();

        ResponseStructure<List<MenuItemModel>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("All menu items fetched");
        res.setData(items);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<MenuItemModel>> getById(Integer id) {

        Optional<MenuItemModel> opt = menuItemRepo.findById(id);

        ResponseStructure<MenuItemModel> res = new ResponseStructure<>();

        if (opt.isPresent()) {
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Menu item found");
            res.setData(opt.get());
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            res.setStatusCode(HttpStatus.NOT_FOUND.value());
            res.setMsg("Menu item not found");
            res.setData(null);
            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<MenuItemModel>> updatePrice(MenuItemModel menu) {
        ResponseStructure<MenuItemModel> res = new ResponseStructure<>();
        if (menu.getId()==null)
        {
            throw new IdNotFoundException("id must be passed ");
        }
        Optional<MenuItemModel>opt = menuItemRepo.findById(menu.getId());
        if(opt.isPresent() )
        {
            MenuItemModel existing = opt.get();

            existing.setPrice(menu.getPrice());
            MenuItemModel m=menuItemRepo.save(existing);

            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("price updated successfully");
            res.setData(m);
            return new ResponseEntity<>(res,HttpStatus.OK);

        }
        else
        {
            throw new IdNotFoundException("ID not found in database");
        }
    }


    @Override
    public ResponseEntity<ResponseStructure<MenuItemModel>> deleteMenuItem(Integer id) {

        Optional<MenuItemModel> opt = menuItemRepo.findById(id);
        ResponseStructure<MenuItemModel> res = new ResponseStructure<>();

        if (opt.isPresent()) {

            menuItemRepo.deleteById(id);

            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Menu item deleted successfully");
            res.setData(opt.get());

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {

            res.setStatusCode(HttpStatus.NOT_FOUND.value());
            res.setMsg("Menu item not found");
            res.setData(null);

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> getItemsGreaterThanValue(Double price) {

        List<MenuItemModel> items = menuItemRepo.findByPriceGreaterThan(price);

        ResponseStructure<List<MenuItemModel>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Items fetched with price greater than " + price);
        res.setData(items);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<MenuItemModel>> getItemByName(String itemName) {

        Optional<MenuItemModel> opt = menuItemRepo.getItemByName(itemName);

        ResponseStructure<MenuItemModel> res = new ResponseStructure<>();

        if (opt.isPresent()) {

            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Menu item found");
            res.setData(opt.get());

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {

            res.setStatusCode(HttpStatus.NOT_FOUND.value());
            res.setMsg("Menu item not found");
            res.setData(null);

            return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
    }
}