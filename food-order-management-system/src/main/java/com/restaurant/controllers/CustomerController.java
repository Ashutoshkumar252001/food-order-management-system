package com.restaurant.controllers;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.models.CustomerModel;
import com.restaurant.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<ResponseStructure<CustomerModel>> createCustomer(@RequestBody CustomerModel customer){
        return customerService.createCustomer(customer);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ResponseStructure<List<CustomerModel>>> getAllCustomer(){
        return customerService.getAllCustomer();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<CustomerModel>> getById(@PathVariable Integer id){
        return customerService.getById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseStructure<CustomerModel>> updateCustomer(@RequestBody CustomerModel customer){
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<CustomerModel>> deleteCustomer(@PathVariable Integer id){
        return customerService.deleteCustomer(id);

    }

    @GetMapping("/fetch/{contact}")
    public ResponseEntity<ResponseStructure<CustomerModel>> getCustomerByContact(@PathVariable Long contact){
        return customerService.getCustomerByContact(contact);
    }
}
