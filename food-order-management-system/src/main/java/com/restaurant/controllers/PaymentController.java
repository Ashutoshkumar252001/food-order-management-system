package com.restaurant.controllers;


import com.restaurant.dto.ResponseStructure;
import com.restaurant.enums.PaymentMethod;
import com.restaurant.enums.PaymentStatus;
import com.restaurant.models.PaymentModel;
import com.restaurant.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<PaymentModel>> getPaymentById(@PathVariable Integer id) {

        return paymentService.getPaymentById(id);
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseStructure<List<PaymentModel>>> getPaymentByStatus(@RequestParam PaymentStatus paymentStatus) {

        return paymentService.getPaymentByStatus(paymentStatus);
    }

    @GetMapping("/method")
    public ResponseEntity<ResponseStructure<List<PaymentModel>>> getPaymentByPaymentMethod(@RequestParam PaymentMethod paymentMethod) {

        return paymentService.getPaymentByPaymentMethod(paymentMethod);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<PaymentModel>> updatePaymentStatus(@PathVariable Integer id, @RequestParam PaymentStatus status) {

        return paymentService.updatePaymentStatus(id, status);
    }
}
