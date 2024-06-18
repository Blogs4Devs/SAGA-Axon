package com.demo.saga.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompleteShipEvent {
    private String shipmentId;
    private String paymentId;
    private String orderId;
    private String userId;
    private String shipmentStatus;
    private String comment;
}