package com.restaurant.validator;

import com.restaurant.models.CustomerModel;
import com.restaurant.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CustomerValidation implements DataValidator{
    @Autowired
    private CustomerRepo customerRepo;
    @Override
    public List<String> validate(Object data) {
        List<String> errors = new ArrayList<>();
        CustomerModel customer = (CustomerModel) data;
        if (customer.getContact() == null || !customer.getContact().equals("\\d{10}")) {
            errors.add("Phone number must be 10 digits");
        }


        return errors;
    }
}
