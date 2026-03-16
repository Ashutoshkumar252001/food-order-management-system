package com.restaurant.services;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.models.MenuItemModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MenuItemService {
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> addMenuItems(List<MenuItemModel> menu);
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> getAllMenuItems(List<MenuItemModel> menu);
    public ResponseEntity<ResponseStructure<MenuItemModel>> getById(Integer id);
    public ResponseEntity<ResponseStructure<MenuItemModel>> updatePrice(MenuItemModel menu);
    public ResponseEntity<ResponseStructure<MenuItemModel>> deleteMenuItem(Integer id);
    public ResponseEntity<ResponseStructure<List<MenuItemModel>>> getItemsGreaterThanValue(Double price);
    public ResponseEntity<ResponseStructure<MenuItemModel>> getItemByName(String itemName);
}
