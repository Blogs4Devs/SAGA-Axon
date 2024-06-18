package com.demo.saga.core.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private String productId;
    private String userId;
    private String quantity;
    private String price;
    private String orderStatus;

}