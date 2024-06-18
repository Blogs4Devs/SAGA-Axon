package com.demo.saga.core.commands;

import com.demo.saga.core.models.ServiceTypes;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CancelOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private String orderStatus = ServiceTypes.ORDER_STATUS.CANCELLED.toString();
}