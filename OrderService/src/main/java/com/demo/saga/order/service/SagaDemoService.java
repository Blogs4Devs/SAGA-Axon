package com.demo.saga.order.service;

import java.util.UUID;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.saga.core.commands.CreateOrderCommand;
import com.demo.saga.core.models.OrderObject;
import com.demo.saga.core.models.ServiceTypes;

@Service
public class SagaDemoService {

    @Autowired
    private  CommandGateway commandGateway;

    public String createOrder(OrderObject orderRestModel) {
        //Generate an Order ID
        String orderId = UUID.randomUUID().toString();
        //Create an CreateOrderCommand
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .price(orderRestModel.getPrice())
                .productId(orderRestModel.getProductId())
                .quantity(orderRestModel.getQuantity())
                .userId(orderRestModel.getUserId())
                .orderStatus(ServiceTypes.ORDER_STATUS.ORDERED.toString())
                .build();
        //Initiate the transaction
        //It sends a command to the system and waits for a response. The method likely blocks the execution until a response is received
        Object result = commandGateway.sendAndWait(createOrderCommand);

        return "Your order has been initiated. Order# "+result;
    }
}