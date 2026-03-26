package com.restaurant.services;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.enums.PaymentMethod;
import com.restaurant.enums.PaymentStatus;
import com.restaurant.models.PaymentModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PaymentService {

    public ResponseEntity<ResponseStructure<PaymentModel>> getPaymentById(Integer paymentId);

    public ResponseEntity<ResponseStructure<List<PaymentModel>>> getPaymentByStatus(PaymentStatus status);

    public ResponseEntity<ResponseStructure<List<PaymentModel>>> getPaymentByPaymentMethod(PaymentMethod paymentMethod);



    public ResponseEntity<ResponseStructure<PaymentModel>> updatePaymentStatus(Integer paymentId, PaymentStatus status);



}