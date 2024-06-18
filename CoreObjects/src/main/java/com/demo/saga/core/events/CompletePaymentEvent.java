package com.demo.saga.core.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompletePaymentEvent {
    private String paymentId;
    private String orderId;
    private String userId;
    private String price;
    private String paymentStatus;
    private String comment;
}