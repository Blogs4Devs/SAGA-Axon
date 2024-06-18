package com.demo.saga.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.saga.core.models.OrderObject;
import com.demo.saga.order.service.SagaDemoService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    SagaDemoService _service;

    @PostMapping
    public String createOrder(@RequestBody OrderObject orderRestModel) {
        return _service.createOrder(orderRestModel);
    }
}