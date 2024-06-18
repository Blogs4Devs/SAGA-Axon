package com.demo.saga.order.saga;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.saga.core.commands.CancelOrderCommand;
import com.demo.saga.core.commands.CancelPaymentCommand;
import com.demo.saga.core.commands.CompleteOrderCommand;
import com.demo.saga.core.commands.CompletePaymentCommand;
import com.demo.saga.core.commands.CompleteShipCommand;
import com.demo.saga.core.events.CancelOrderEvent;
import com.demo.saga.core.events.CancelPaymentEvent;
import com.demo.saga.core.events.CompleteOrderEvent;
import com.demo.saga.core.events.CompletePaymentEvent;
import com.demo.saga.core.events.CompleteShipEvent;
import com.demo.saga.core.events.OrderCreateEvent;

@Saga
public class OrderSystemSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    public OrderSystemSaga() {}

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    private void handle(OrderCreateEvent event) {

        //CreateOrderCommand triggered the OrderCreateEvent
        //Now we can create the next transaction step which is payment COMMAND
        String paymentId = UUID.randomUUID().toString();
        //Associate the payment ID
        //This means that the payment process, identified by paymentId, is now part of the Saga.
        // The system can track the payment process as part of the overall order transaction.
        // If the payment fails, the system can use the paymentId to identify the payment
        // process and decide on the appropriate recovery action, such as refunding the order
        // or compensating the delivery service.
        SagaLifecycle.associateWith("paymentId", paymentId);
        //Create a command to make payment
        CompletePaymentCommand command = CompletePaymentCommand.builder()
                .orderId(event.getOrderId())
                .paymentId(paymentId)
                .price(event.getPrice())
                .userId(event.getUserId())
                .build();
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "paymentId")
    private void handle(CompletePaymentEvent event) {
        //IF PAYMENT ERROR ROLL BACK ORDER
        if(event.getPaymentStatus().equalsIgnoreCase("ERROR")) {
            cancelOrderCommand(event.getOrderId());
        }else {
            String shipmentId = UUID.randomUUID().toString();
            //Associate the shipment ID
            SagaLifecycle.associateWith("shipmentId", shipmentId);

            //Trigger the next flow which is the Shipping Command
            CompleteShipCommand command = CompleteShipCommand.builder()
                    .orderId(event.getOrderId())
                    .shipmentId(shipmentId)
                    .paymentId(event.getPaymentId())
                    .userId(event.getUserId())
                    .build();
            commandGateway.send(command);
        }
    }

    //Initiate the Cancel Order Command
    private void cancelOrderCommand(String orderId) {
        CancelOrderCommand command = CancelOrderCommand.builder()
                .orderId(orderId).build();
        commandGateway.send(command);
    }

    //Cancel Payment Command
    private void cancelPaymentCommand(CompleteShipEvent event) {
        CancelPaymentCommand cancelPaymentCommand =  CancelPaymentCommand.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .build();
        commandGateway.send(cancelPaymentCommand);
    }

    @SagaEventHandler(associationProperty = "shipmentId")
    public void handle(CompleteShipEvent event) {
        //IF SHIPMENT ERROR ROLL BACK ORDER
        if(event.getShipmentStatus().equalsIgnoreCase("ERROR")) {
            cancelPaymentCommand(event);
        }else {
            //SagaLifecycle.associateWith("orderId", event.getOrderId());
            //Ship is complete, Complete the order
            CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                    .orderId(event.getOrderId())
                    .build();
            commandGateway.send(completeOrderCommand);
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(CancelPaymentEvent event) {
        //Trigger the cancel Order
        cancelOrderCommand(event.getOrderId());
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(CompleteOrderEvent event) {
        //Handle Event if necessary
        System.out.println("Ordering Complete");
    }

    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(CancelOrderEvent event) {
        //Handle Event if necessary
        System.out.println("Order Cancelled");
    }
}