package com.restaurant.repository;

import com.restaurant.models.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<CustomerModel,Integer> {

    Optional<CustomerModel> findCustomerByContact(Long contact);
    Optional<CustomerModel> findByEmail(String email);

}
