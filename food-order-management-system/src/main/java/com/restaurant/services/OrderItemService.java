package com.restaurant.services;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.models.OrderItemModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderItemService {
    public ResponseEntity<ResponseStructure<OrderItemModel>> addItemToOrder(OrderItemModel orderItem);
    public ResponseEntity<ResponseStructure<OrderItemModel>> updateQuantity(OrderItemModel orderItem );
    public ResponseEntity<ResponseStructure<OrderItemModel>> removeItem(Integer id);
    public ResponseEntity<ResponseStructure<List<OrderItemModel>>> getItemByOrderId(Integer Id);


}
