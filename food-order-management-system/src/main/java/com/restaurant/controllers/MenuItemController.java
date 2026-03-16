package com.restaurant.controllers;


import com.restaurant.dto.ResponseStructure;
import com.restaurant.models.MenuItemModel;
import com.restaurant.services.MenuItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping("/add")
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> addMenuItems(
            @RequestBody List<MenuItemModel> menu){

        return menuItemService.addMenuItems(menu);
    }


    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> getAllMenuItems(){

        return menuItemService.getAllMenuItems(null);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<MenuItemModel>> getById(
            @PathVariable Integer id){

        return menuItemService.getById(id);
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseStructure<MenuItemModel>> updatePrice(
            @RequestBody MenuItemModel menu){

        return menuItemService.updatePrice(menu);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseStructure<MenuItemModel>> deleteMenuItem(
            @PathVariable Integer id){

        return menuItemService.deleteMenuItem(id);
    }


    @GetMapping("/price/{price}")
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> getItemsGreaterThanValue(
            @PathVariable Double price){

        return menuItemService.getItemsGreaterThanValue(price);
    }


    @GetMapping("/name/{itemName}")
    public ResponseEntity<ResponseStructure<MenuItemModel>> getItemByName(
            @PathVariable String itemName){

        return menuItemService.getItemByName(itemName);
    }

}
