package com.demo.saga.payment.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.demo.saga.core.commands.CancelPaymentCommand;
import com.demo.saga.core.commands.CompletePaymentCommand;
import com.demo.saga.core.events.CancelPaymentEvent;
import com.demo.saga.core.events.CompletePaymentEvent;
import com.demo.saga.payment.handler.PaymentEventHandler;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;

    public PaymentAggregate() {}

    @CommandHandler
    public PaymentAggregate(CompletePaymentCommand command) {
        CompletePaymentEvent event = CompletePaymentEvent.builder()
                .orderId(command.getOrderId())
                .paymentId(command.getPaymentId())
                .paymentStatus(command.getPaymentStatus())
                .userId(command.getUserId())
                .price(command.getPrice())
                .build();
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void handle(CancelPaymentCommand command) {
        CancelPaymentEvent event = CancelPaymentEvent.builder()
                .orderId(command.getOrderId())
                .paymentId(command.getPaymentId())
                .paymentStatus("Payment Cancelled")
                .price(command.getPrice())
                .userId(command.getUserId())
                .build();
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(CompletePaymentEvent event, PaymentEventHandler nh) {
        this.paymentId = event.getPaymentId();
        nh.onCompletePaymentEvent(event);
    }

    @EventSourcingHandler
    public void on(CancelPaymentEvent event, PaymentEventHandler nh) {
        this.paymentId = event.getPaymentId();
        nh.onCancelPaymentEvent(event);
    }
}