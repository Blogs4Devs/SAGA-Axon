package com.demo.saga.order.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.demo.saga.core.models.Order;

public interface OrderRepository extends MongoRepository<Order, String> {
    Order findByOrderId(String orderId);
}
