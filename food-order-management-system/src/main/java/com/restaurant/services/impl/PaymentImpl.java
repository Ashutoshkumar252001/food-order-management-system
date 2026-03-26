package com.restaurant.services.impl;

import com.restaurant.dto.ResponseStructure;
import com.restaurant.enums.PaymentMethod;
import com.restaurant.enums.PaymentStatus;
import com.restaurant.exceptions.IdNotFoundException;
import com.restaurant.models.PaymentModel;
import com.restaurant.repository.PaymentRepo;
import com.restaurant.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentImpl implements PaymentService {
    @Autowired
    private PaymentRepo paymentRepo;


    @Override
    public ResponseEntity<ResponseStructure<PaymentModel>> getPaymentById(Integer Id) {
        Optional<PaymentModel> opt = paymentRepo.findById(Id);

        if (opt.isPresent()) {
            ResponseStructure<PaymentModel> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Payment fetched successfully");
            res.setData(opt.get());
            return new ResponseEntity<>(res,HttpStatus.OK);
        } else {
            throw new IdNotFoundException("Payment not found with id: " + Id);
        }

    }




    @Override
    public ResponseEntity<ResponseStructure<List<PaymentModel>>> getPaymentByStatus(PaymentStatus paymentStatus) {
        List<PaymentModel> list = paymentRepo.findByPaymentStatus(paymentStatus);

        ResponseStructure<List<PaymentModel>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Payments fetched by status");
        res.setData(list);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<List<PaymentModel>>> getPaymentByPaymentMethod(PaymentMethod paymentMethod) {
        List<PaymentModel> list = paymentRepo.findByPaymentMethod(paymentMethod);

        ResponseStructure<List<PaymentModel>> res = new ResponseStructure<>();
        res.setStatusCode(HttpStatus.OK.value());
        res.setMsg("Payments fetched by method");
        res.setData(list);

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseStructure<PaymentModel>> updatePaymentStatus(Integer Id, PaymentStatus status) {
        Optional<PaymentModel> opt = paymentRepo.findById(Id);

        if (opt.isPresent()) {
            PaymentModel payment = opt.get();

            payment.setPaymentStatus(status);

            PaymentModel updated = paymentRepo.save(payment);

            ResponseStructure<PaymentModel> res = new ResponseStructure<>();
            res.setStatusCode(HttpStatus.OK.value());
            res.setMsg("Payment status updated successfully");
            res.setData(updated);

            return new ResponseEntity<>(res,HttpStatus.OK);
        } else {
            throw new IdNotFoundException("Payment not found with id: " + Id);
        }

    }
}
