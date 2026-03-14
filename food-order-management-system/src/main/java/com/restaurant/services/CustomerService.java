package com.restaurant.services;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.models.CustomerModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<ResponseStructure<CustomerModel>> createCustomer(CustomerModel customer);
    ResponseEntity<ResponseStructure<List<CustomerModel>>> getAllCustomer();
    ResponseEntity<ResponseStructure<CustomerModel>> getById(Integer id);
    ResponseEntity<ResponseStructure<CustomerModel>> updateCustomer(CustomerModel customer);
    ResponseEntity<ResponseStructure<CustomerModel>> deleteCustomer(Integer id);
    ResponseEntity<ResponseStructure<CustomerModel>> getCustomerByContact(Long contact);

}
