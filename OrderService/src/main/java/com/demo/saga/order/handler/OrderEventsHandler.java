package com.demo.saga.order.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.saga.core.events.CancelOrderEvent;
import com.demo.saga.core.events.CompleteOrderEvent;
import com.demo.saga.core.events.OrderCreateEvent;
import com.demo.saga.core.models.Order;
import com.demo.saga.order.repository.OrderRepository;

@Component
public class OrderEventsHandler {

    @Autowired
    private OrderRepository orderRepository;

    public void onOrderCreateEvent(OrderCreateEvent event){
        Order order = orderRepository.findByOrderId(event.getOrderId());
        if(order==null) {
            order = new Order();
            order.setOrderId(event.getOrderId());
            order.setOrderStatus(event.getOrderStatus());
            order.setPrice(event.getPrice());
            order.setProductId(event.getProductId());
            order.setQuantity(event.getQuantity());
            order.setUserId(event.getUserId());
            order.setComment("Order Initiated");
            orderRepository.save(order);
        }
    }

    public void onCompleteOrderEvent(CompleteOrderEvent event) {
        Order order = orderRepository.findByOrderId(event.getOrderId());
        if(order!=null) {
            order.setOrderStatus(event.getOrderStatus());
            order.setComment("Successfully created order");
            orderRepository.save(order);
        }
    }

    public void onCancelOrderEvent(CancelOrderEvent event) {
        Order order = orderRepository.findByOrderId(event.getOrderId());
        if(order!=null) {
            order.setOrderStatus("Cancelled");
            order.setComment("Order was cancelled due to an error.");
            orderRepository.save(order);
        }
    }
}