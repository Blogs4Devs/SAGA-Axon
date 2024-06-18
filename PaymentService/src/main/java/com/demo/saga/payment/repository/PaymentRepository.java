package com.demo.saga.payment.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.demo.saga.core.models.Payment;

public interface PaymentRepository extends MongoRepository<Payment,String> {
    Payment findByPaymentId(String paymentId);
}