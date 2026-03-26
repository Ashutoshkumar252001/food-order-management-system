package com.restaurant.repository;

import com.restaurant.enums.PaymentMethod;
import com.restaurant.enums.PaymentStatus;
import com.restaurant.models.PaymentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepo extends JpaRepository<PaymentModel,Integer> {

    List<PaymentModel> findByPaymentStatus(PaymentStatus paymentStatus);

    List<PaymentModel> findByPaymentMethod(PaymentMethod paymentMethod);
}
