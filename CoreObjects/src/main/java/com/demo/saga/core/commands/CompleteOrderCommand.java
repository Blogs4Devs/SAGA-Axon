package com.demo.saga.core.commands;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import com.demo.saga.core.models.ServiceTypes;
import lombok.Builder;
import lombok.Value;
@Value
@Builder
public class CompleteOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private String orderStatus = ServiceTypes.ORDER_STATUS.ORDERED.toString();
}
