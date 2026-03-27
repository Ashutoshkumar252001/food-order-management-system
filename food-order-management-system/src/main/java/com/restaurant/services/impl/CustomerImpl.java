package com.restaurant.services.impl;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.exceptions.IdNotFoundException;
import com.restaurant.exceptions.NoRecordAvailableException;
import com.restaurant.models.CustomerModel;
import com.restaurant.repository.CustomerRepo;
import com.restaurant.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerImpl implements CustomerService {
    @Autowired
    private CustomerRepo customerRepo;

    @Override
    public ResponseEntity<ResponseStructure<CustomerModel>> createCustomer(CustomerModel customer)
    {
        ResponseStructure<CustomerModel> res = new ResponseStructure<>();
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new RuntimeException("Email is required");
        }

        Optional<CustomerModel> emailOpt = customerRepo.findByEmail(customer.getEmail());
        if (emailOpt.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (customer.getContact() == null) {
            throw new RuntimeException("Contact is required");
        }

        String contactStr = String.valueOf(customer.getContact());

        if (contactStr.length() != 10) {
            throw new RuntimeException("Contact number must be 10 digits");
        }

        Optional<CustomerModel> contactOpt = customerRepo.findCustomerByContact(customer.getContact());
        if (contactOpt.isPresent()) {
            throw new RuntimeException("Contact already exists");
        }


        CustomerModel c = customerRepo.save(customer);
       res.setStatusCode(HttpStatus.CREATED.value());
       res.setMsg("customer is created");
       res.setData(c);
       return new ResponseEntity<>(res,HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<ResponseStructure<List<CustomerModel>>> getAllCustomer()
    {
        ResponseStructure<List<CustomerModel>> res = new ResponseStructure<>();
        List<CustomerModel> cus = customerRepo.findAll();
        res.setStatusCode(HttpStatus.CONTINUE.value());
        res.setMsg("Fetches all customer records");
        res.setData(cus);


        return new ResponseEntity<>(res,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseStructure<CustomerModel>> getById(Integer id)
    {
        ResponseStructure<CustomerModel> res = new ResponseStructure<>();
       Optional<CustomerModel>opt = customerRepo.findById(id);
       if(opt.isPresent())
       {
           CustomerModel c = opt.get();
           res.setStatusCode(HttpStatus.PROCESSING.value());
           res.setMsg("customer is available");
           res.setData(c);
           return new ResponseEntity<>(res,HttpStatus.CREATED);
       }
       else
       {
           throw new IdNotFoundException("Id is not Available");
       }
    }

    @Override
    public ResponseEntity<ResponseStructure<CustomerModel>> updateCustomer(CustomerModel customer) {

        ResponseStructure<CustomerModel> res = new ResponseStructure<>();

        if (customer.getId() == null) {
            throw new IdNotFoundException("id must be passed");
        }

        Optional<CustomerModel> opt = customerRepo.findById(customer.getId());

        if (opt.isEmpty()) {
            throw new IdNotFoundException("Customer not found");
        }

        CustomerModel existing = opt.get();

        Optional<CustomerModel> emailOpt = customerRepo.findByEmail(customer.getEmail());
        if (emailOpt.isPresent() && !emailOpt.get().getId().equals(customer.getId())) {
            throw new RuntimeException("Email already exists");
        }

        String contactStr = String.valueOf(customer.getContact());
        if (contactStr.length() != 10) {
            throw new RuntimeException("Contact must be 10 digits");
        }

        Optional<CustomerModel> contactOpt = customerRepo.findCustomerByContact(customer.getContact());
        if (contactOpt.isPresent() && !contactOpt.get().getId().equals(customer.getId())) {
            throw new RuntimeException("Contact already exists");
        }

        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        existing.setContact(customer.getContact());
        existing.setAddress(customer.getAddress());

        CustomerModel updated = customerRepo.save(existing);

        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Customer updated successfully");
        res.setData(updated);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ResponseStructure<CustomerModel>> deleteCustomer(Integer id)
    {
        ResponseStructure<CustomerModel> res = new ResponseStructure<>();
        Optional<CustomerModel> opt = customerRepo.findById(id);
        if(opt.isPresent())
        {
            CustomerModel c = opt.get();
            customerRepo.delete(c);
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Customer is deleted");
            res.setData(c);
            return new ResponseEntity<>(res,HttpStatus.OK);

        }
        throw new IdNotFoundException("Id is not available in database");
    }

    @Override
    public ResponseEntity<ResponseStructure<CustomerModel>> getCustomerByContact(Long contact)
    {
        ResponseStructure<CustomerModel> res = new ResponseStructure<>();
        Optional<CustomerModel> opt = customerRepo.findCustomerByContact(contact);
        if(opt.isPresent())
        {
            CustomerModel c = opt.get();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("contact is available");
            res.setData(c);
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        throw new NoRecordAvailableException("this Contact no is not available");
    }
}
